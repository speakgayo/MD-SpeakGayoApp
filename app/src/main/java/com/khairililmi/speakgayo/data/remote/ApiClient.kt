package com.khairililmi.speakgayo.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://apimodelgayo-7w4hmjrhhq-et.a.run.app/"
    private const val TOKEN = "1b300e3e12d6bb97ecb452523639a095"

    private val client: OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(Interceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $TOKEN")
                .build()
            chain.proceed(newRequest)
        })
    }.build()

    private var retrofit: Retrofit? = null

    val service: ApiService
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            }
            return retrofit!!.create(ApiService::class.java)
        }
}
