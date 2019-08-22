package com.upday.updaytask.data.remote.images

import com.google.gson.annotations.SerializedName

data class ImagesResponse (
    @SerializedName("data")
    var data: ArrayList<Image>
)