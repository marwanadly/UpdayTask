package com.upday.updaytask.util

import android.content.Context

class Constants {

    companion object {
        const val PREFERENCES_NAME = "updayTask"
        const val PREFERENCES_MODE = Context.MODE_PRIVATE
        const val TOKEN_PREFERENCE = "access_token"
    }

    class ApiConstants{
        companion object {
            const val CLIENT_ID = "1f023-2276e-3d0d3-f0f66-83f5d-1736d"
            const val CLIENT_SECRET = "6326d-8751e-23e06-3f1af-ff168-dc3ad"
            const val GRANT_TYPE = "client_credentials"
            const val TOKEN_REALM = "customer"
            const val GET_TOKEN_SUCCESS = 0
            const val GET_TOKEN_FAILED = 1
            const val IMAGES_LOADED_SUCCESSFULLY = 0
            const val IMAGES_LOAD_FAILURE = 1
            const val IMAGES_LOAD_TIMEOUT = 2
            const val IMAGES_LOAD_RETRY = 3
        }
    }
}