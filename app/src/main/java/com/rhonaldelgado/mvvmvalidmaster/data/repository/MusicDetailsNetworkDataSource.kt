package com.rhonaldelgado.mvvmvalidmaster.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rhonaldelgado.mvvmvalidmaster.data.api.TheMusicDBInterface
import com.rhonaldelgado.mvvmvalidmaster.data.vo.MusicDetails
import io.reactivex.disposables.CompositeDisposable

class MusicDetailsNetworkDataSource (private val apiService : TheMusicDBInterface, private val compositeDisposable: CompositeDisposable) {

    private val _networkState  = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedMusicDetailsResponse =  MutableLiveData<MusicDetails>()
    val downloadedMusicResponse: LiveData<MusicDetails>
        get() = _downloadedMusicDetailsResponse


}