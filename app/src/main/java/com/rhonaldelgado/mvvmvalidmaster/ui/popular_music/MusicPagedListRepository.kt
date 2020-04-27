package com.rhonaldelgado.mvvmvalidmaster.ui.popular_music

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rhonaldelgado.mvvmvalidmaster.data.api.POST_PER_PAGE
import com.rhonaldelgado.mvvmvalidmaster.data.api.TheMusicDBInterface
import com.rhonaldelgado.mvvmvalidmaster.data.api.POST_PER_PAGE
import com.rhonaldelgado.mvvmvalidmaster.data.repository.MusicDataSource
import com.rhonaldelgado.mvvmvalidmaster.data.repository.MusicDataSourceFactory
import com.rhonaldelgado.mvvmvalidmaster.data.repository.NetworkState
import com.rhonaldelgado.mvvmvalidmaster.data.vo.Music
import io.reactivex.disposables.CompositeDisposable

class MusicPagedListRepository (private val apiService : TheMusicDBInterface) {

    lateinit var musicPagedList: LiveData<PagedList<Music>>
    lateinit var musicsDataSourceFactory: MusicDataSourceFactory

    fun fetchLiveMusicPagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Music>> {
        musicsDataSourceFactory = MusicDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Integer.parseInt(POST_PER_PAGE))
            .build()

        musicPagedList = LivePagedListBuilder(musicsDataSourceFactory, config).build()

        return musicPagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MusicDataSource, NetworkState>(
            musicsDataSourceFactory.musicsLiveDataSource, MusicDataSource::networkState)
    }
}