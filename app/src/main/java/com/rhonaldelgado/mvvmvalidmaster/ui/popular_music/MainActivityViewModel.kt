package com.rhonaldelgado.mvvmvalidmaster.ui.popular_music

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.rhonaldelgado.mvvmvalidmaster.data.repository.NetworkState
import com.rhonaldelgado.mvvmvalidmaster.data.vo.Music
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val musicRepository : MusicPagedListRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  musicPagedList : LiveData<PagedList<Music>> by lazy {
        musicRepository.fetchLiveMusicPagedList(compositeDisposable)
    }

    val  networkState : LiveData<NetworkState> by lazy {
        musicRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return musicPagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}