package com.rhonaldelgado.mvvmvalidmaster.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.rhonaldelgado.mvvmvalidmaster.data.api.FIRST_PAGE
import com.rhonaldelgado.mvvmvalidmaster.data.api.TheMusicDBInterface
import com.rhonaldelgado.mvvmvalidmaster.data.vo.Music
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MusicDataSource (private val apiService : TheMusicDBInterface, private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Int, Music>(){

    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Music>) {

        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularMusic(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.topartists.musicList, null, Integer.parseInt(page)+1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MusicDataSource", it.message)
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Music>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularMusic(params.key.toString())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.topartists.attr.totalPages >= params.key.toString()) {
                            callback.onResult(it.topartists.musicList, params.key+1)
                            networkState.postValue(NetworkState.LOADED)
                        }
                        else{
                            networkState.postValue(NetworkState.ENDOFLIST)
                        }
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MusicDataSource", it.message)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Music>) {

    }
}