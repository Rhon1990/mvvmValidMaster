package com.rhonaldelgado.mvvmvalidmaster.data.vo


import com.google.gson.annotations.SerializedName

data class BasicInfo(
    @SerializedName("artist")
    val musicList: List<Music>,
    @SerializedName("@attr")
    val attr: Infoattr
)