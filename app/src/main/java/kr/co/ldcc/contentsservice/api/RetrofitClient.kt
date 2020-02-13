package kr.co.ldcc.contentsservice.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    var retrofit = Retrofit.Builder()
        .baseUrl("https://dapi.kakao.com")
        .addConverterFactory(GsonConverterFactory.create()) // 파싱등록
        .build()

    var service = retrofit.create(RetrofitService::class.java)

    companion object{
        private val ourInstance: RetrofitClient = RetrofitClient()
        fun getInstance(): RetrofitClient {
            return ourInstance
        }
    }
}