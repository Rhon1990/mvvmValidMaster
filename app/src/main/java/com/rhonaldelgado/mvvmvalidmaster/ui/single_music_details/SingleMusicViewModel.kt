package com.rhonaldelgado.mvvmvalidmaster.ui.single_music_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rhonaldelgado.mvvmvalidmaster.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class SingleMusicViewModel (private val musicRepository : MusicDetailsRepository)  : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val networkState : LiveData<NetworkState> by lazy {
        musicRepository.getMusicDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }



}