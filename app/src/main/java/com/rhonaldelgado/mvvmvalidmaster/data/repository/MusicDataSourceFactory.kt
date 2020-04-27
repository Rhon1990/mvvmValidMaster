package com.rhonaldelgado.mvvmvalidmaster.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.rhonaldelgado.mvvmvalidmaster.data.api.TheMusicDBInterface
import com.rhonaldelgado.mvvmvalidmaster.data.vo.Music
import io.reactivex.disposables.CompositeDisposable

class MusicDataSourceFactory (private val apiService : TheMusicDBInterface, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, Music>() {

    val musicsLiveDataSource =  MutableLiveData<MusicDataSource>()

    override fun create(): DataSource<Int, Music> {
        val musicDataSource = MusicDataSource(apiService,compositeDisposable)

        musicsLiveDataSource.postValue(musicDataSource)
        return musicDataSource
    }
}