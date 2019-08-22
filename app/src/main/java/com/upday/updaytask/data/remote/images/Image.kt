package com.upday.updaytask.data.remote.images

import com.google.gson.annotations.SerializedName

data class Image (
    @SerializedName("assets")
    var assets: Assets
)