package com.bogdan.mochacasestudy.app.data.model

import com.google.gson.annotations.SerializedName

data class ImageModel(
    val id: String,
    @SerializedName("download_url")
    val url: String
)