package com.rhonaldelgado.mvvmvalidmaster.ui.popular_music

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.rhonaldelgado.mvvmvalidmaster.R
import com.rhonaldelgado.mvvmvalidmaster.data.api.TheMusicDBClient
import com.rhonaldelgado.mvvmvalidmaster.data.api.TheMusicDBInterface
import com.rhonaldelgado.mvvmvalidmaster.data.repository.NetworkState
import com.rhonaldelgado.mvvmvalidmaster.ui.single_music_details.SingleMusic
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    lateinit var musicRepository: MusicPagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService : TheMusicDBInterface = TheMusicDBClient.getClient()

        musicRepository = MusicPagedListRepository(apiService)

        viewModel = getViewModel()

        val musicAdapter = PopularMusicPagedListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = musicAdapter.getItemViewType(position)
                if (viewType == musicAdapter.MUSIC_VIEW_TYPE) return  1    // Music_VIEW_TYPE will occupy 1 out of 3 span
                else return 3                                              // NETWORK_VIEW_TYPE will occupy all 3 span
            }
        };


        rv_music_list.layoutManager = gridLayoutManager
        rv_music_list.setHasFixedSize(true)
        rv_music_list.adapter = musicAdapter

        viewModel.musicPagedList.observe(this, Observer {
            musicAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                musicAdapter.setNetworkState(it)
            }
        })

    }


    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(musicRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }

}
