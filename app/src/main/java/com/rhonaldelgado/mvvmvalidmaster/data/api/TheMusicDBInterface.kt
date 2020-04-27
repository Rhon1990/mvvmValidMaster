package com.rhonaldelgado.mvvmvalidmaster.data.api

import com.rhonaldelgado.mvvmvalidmaster.data.vo.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMusicDBInterface {

    //http://ws.audioscrobbler.com/2.0/?method=geo.gettopartists&country=spain&api_key=829751643419a7128b7ada50de590067&format=json


    @GET("?method=geo.gettopartists&country=spain&format=json&")
    fun getPopularMusic(@Query("page") page: String): Single<MusicResponse>

}