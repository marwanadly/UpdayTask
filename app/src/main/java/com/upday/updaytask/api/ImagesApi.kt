package com.upday.updaytask.api

import com.upday.updaytask.data.remote.images.ImagesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ImagesApi {
    @GET("images/search")
    fun getImages(@Header("Authorization") access_token:String, @Query("per_page") perPage:Int, @Query("page") page:Int): Observable<ImagesResponse>
}