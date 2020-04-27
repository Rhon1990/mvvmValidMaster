package com.rhonaldelgado.mvvmvalidmaster.data.vo


import com.google.gson.annotations.SerializedName

data class ImageList(
    @SerializedName("#text")
    val text: String,
    @SerializedName("size")
    val size: String
)