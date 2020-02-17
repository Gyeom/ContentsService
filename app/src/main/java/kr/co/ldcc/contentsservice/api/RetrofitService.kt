package kr.co.ldcc.contentsservice.api

import kr.co.ldcc.contentsservice.model.ImageResponse
import kr.co.ldcc.contentsservice.model.VideoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitService {
    @GET("/v2/search/vclip")
    fun getVideo(
        @Header("Authorization") authorization: String,
        @Query("query") query: String
    ): Call<VideoResponse>
    @GET("/v2/search/image")
    fun getImage(
        @Header("Authorization") authorization: String,
        @Query("query") query: String
    ): Call<ImageResponse>
}