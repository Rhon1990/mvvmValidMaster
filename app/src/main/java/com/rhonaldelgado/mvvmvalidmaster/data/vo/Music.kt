package com.rhonaldelgado.mvvmvalidmaster.data.vo


import com.google.gson.annotations.SerializedName

data class Music(
    @SerializedName("name")
    val name: String,
    @SerializedName("listeners")
    val listeners: String,
    @SerializedName("mbid")
    val mbid: String,
    @SerializedName("streamable")
    val streamable: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("image")
    val imageList: List<ImageList>
)