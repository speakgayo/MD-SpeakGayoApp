package com.khairililmi.speakgayo.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("translate-indo-gayo")
    fun translateIndoToGayo(@Body request: TranslateRequest): Call<TranslateResponse>

    @POST("translate-gayo-indo")
    fun translateGayoToIndo(@Body request: TranslateRequest): Call<TranslateResponse>
}
