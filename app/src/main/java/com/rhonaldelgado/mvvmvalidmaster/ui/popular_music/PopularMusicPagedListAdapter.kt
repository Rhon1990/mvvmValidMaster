package com.rhonaldelgado.mvvmvalidmaster.ui.popular_music

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rhonaldelgado.mvvmvalidmaster.R
import com.rhonaldelgado.mvvmvalidmaster.data.repository.NetworkState
import com.rhonaldelgado.mvvmvalidmaster.data.vo.Music
import com.rhonaldelgado.mvvmvalidmaster.ui.single_music_details.SingleMusic
import kotlinx.android.synthetic.main.music_list_item.view.*
import kotlinx.android.synthetic.main.network_state_item.view.*
import android.widget.Toast




class PopularMusicPagedListAdapter(public val context: Context) : PagedListAdapter<Music, RecyclerView.ViewHolder>(MusicDiffCallback()) {

    val MUSIC_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == MUSIC_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.music_list_item, parent, false)
            return MusicItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            return NetworkStateItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MUSIC_VIEW_TYPE) {
            (holder as MusicItemViewHolder).bind(getItem(position),context)
        }
        else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }


    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            MUSIC_VIEW_TYPE
        }
    }




    class MusicDiffCallback : DiffUtil.ItemCallback<Music>() {
        override fun areItemsTheSame(oldItem: Music, newItem: Music): Boolean {
            return oldItem.mbid == newItem.mbid
        }

        override fun areContentsTheSame(oldItem: Music, newItem: Music): Boolean {
            return oldItem == newItem
        }

    }


    class MusicItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bind(music: Music?,context: Context) {
            itemView.cv_music_title.text = music?.name
            itemView.cv_music_release_date.text =  music?.listeners

            val musicPosterURL = music?.imageList?.get(2)?.text
            Glide.with(itemView.context)
                .load(musicPosterURL)
                .into(itemView.cv_iv_music_poster);

            itemView.setOnClickListener{
                val intent = Intent(context, SingleMusic::class.java)
                intent.putExtra("url", music?.url)
                context.startActivity(intent)
            }

        }

    }

    class NetworkStateItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bind(networkState: NetworkState?) {
            if (networkState != null && networkState == NetworkState.LOADING) {
                itemView.progress_bar_item.visibility = View.VISIBLE;
            }
            else  {
                itemView.progress_bar_item.visibility = View.GONE;
            }

            if (networkState != null && networkState == NetworkState.ERROR) {
                itemView.error_msg_item.visibility = View.VISIBLE;
                itemView.error_msg_item.text = networkState.msg;
            }
            else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
                itemView.error_msg_item.visibility = View.VISIBLE;
                itemView.error_msg_item.text = networkState.msg;
            }
            else {
                itemView.error_msg_item.visibility = View.GONE;
            }
        }
    }


    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {                             //hadExtraRow is true and hasExtraRow false
                notifyItemRemoved(super.getItemCount())    //remove the progressbar at the end
            } else {                                       //hasExtraRow is true and hadExtraRow false
                notifyItemInserted(super.getItemCount())   //add the progressbar at the end
            }
        } else if (hasExtraRow && previousState != newNetworkState) { //hasExtraRow is true and hadExtraRow true and (NetworkState.ERROR or NetworkState.ENDOFLIST)
            notifyItemChanged(itemCount - 1)       //add the network message at the end
        }

    }




}