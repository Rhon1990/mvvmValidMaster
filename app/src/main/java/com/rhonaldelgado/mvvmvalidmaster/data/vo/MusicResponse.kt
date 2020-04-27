package com.rhonaldelgado.mvvmvalidmaster.data.vo

import com.google.gson.annotations.SerializedName

data class MusicResponse(
    @SerializedName("topartists")
    val topartists: BasicInfo
)