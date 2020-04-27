package com.rhonaldelgado.mvvmvalidmaster.data.vo


import com.google.gson.annotations.SerializedName

data class Infoattr(
    @SerializedName("country")
    val country: String,
    @SerializedName("page")
    val page: String,
    @SerializedName("perPage")
    val perPage: String,
    @SerializedName("totalPages") //total_pages
    val totalPages: String,
    @SerializedName("total") //total_results
    val total: String

)