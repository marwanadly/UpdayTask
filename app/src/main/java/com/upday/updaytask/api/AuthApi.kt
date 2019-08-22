package com.upday.updaytask.api

import com.upday.updaytask.data.remote.auth.Auth
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("oauth/access_token")
    fun getToken(@Field("client_id") clientID: String,
                 @Field("client_secret") clientSecret: String,
                 @Field("code") code: String,
                 @Field("grant_type") grantType: String,
                 @Field("realm") realm:String): Observable<Auth>
}