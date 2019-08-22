package com.upday.updaytask.data.remote.auth

import com.google.gson.annotations.SerializedName

data class Auth (
    @SerializedName("access_token")
    var token:String
)