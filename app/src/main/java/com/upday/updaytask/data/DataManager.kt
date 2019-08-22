package com.upday.updaytask.data

import com.upday.updaytask.api.AuthApi
import com.upday.updaytask.api.ImagesApi
import com.upday.updaytask.data.preferences.Preferences
import com.upday.updaytask.data.remote.auth.Auth
import com.upday.updaytask.data.remote.images.ImagesResponse
import com.upday.updaytask.util.Constants
import io.reactivex.Observable
import javax.inject.Inject

class DataManager @Inject constructor(
    private val authApi: AuthApi,
    private val imagesApi: ImagesApi,
    private val preferences: Preferences
) {

    fun getToken(
        clientID: String,
        clientSecret: String,
        code: String,
        grantType: String,
        tokenRealm: String
    ): Observable<Auth> = authApi.getToken(clientID, clientSecret, code, grantType, tokenRealm)

    fun getImages(
        pageNumber: Int
    ): Observable<ImagesResponse> =
        imagesApi.getImages(preferences.readString(Constants.TOKEN_PREFERENCE), 10, pageNumber)

    fun saveTokenPreferences(token: String) {
        preferences.writeString(Constants.TOKEN_PREFERENCE, token)
    }
}