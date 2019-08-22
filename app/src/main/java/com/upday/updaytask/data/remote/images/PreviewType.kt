package com.upday.updaytask.data.remote.images

import com.google.gson.annotations.SerializedName

data class PreviewType (
    @SerializedName("url")
    var imageURL: String,
    @SerializedName("height")
    var height: Int,
    @SerializedName("width")
    var width: Int
)