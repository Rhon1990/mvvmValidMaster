package com.rhonaldelgado.mvvmvalidmaster.ui.single_music_details

import androidx.lifecycle.LiveData
import com.rhonaldelgado.mvvmvalidmaster.data.api.TheMusicDBInterface
import com.rhonaldelgado.mvvmvalidmaster.data.repository.MusicDetailsNetworkDataSource
import com.rhonaldelgado.mvvmvalidmaster.data.repository.NetworkState

class MusicDetailsRepository (private val apiService : TheMusicDBInterface) {

    lateinit var musicDetailsNetworkDataSource: MusicDetailsNetworkDataSource


    fun getMusicDetailsNetworkState(): LiveData<NetworkState> {
        return musicDetailsNetworkDataSource.networkState
    }

}