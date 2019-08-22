package com.upday.updaytask.data.remote.images

import com.google.gson.annotations.SerializedName

data class Assets (
    @SerializedName("preview")
    var preview: PreviewType
)