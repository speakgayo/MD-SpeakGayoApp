package com.khairililmi.speakgayo.ui.home

import com.khairililmi.speakgayo.data.remote.ApiClient
import com.khairililmi.speakgayo.data.remote.ApiService
import com.khairililmi.speakgayo.data.remote.TranslateRequest
import com.khairililmi.speakgayo.data.remote.TranslateResponse
import android.util.Log
import com.khairililmi.speakgayo.data.local.favorite.FavoriteDao
import com.khairililmi.speakgayo.data.local.favorite.FavoriteEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TranslateRepository(private val favoriteDao: FavoriteDao) {

    private val apiService: ApiService = ApiClient.service
    companion object{
        const val TRANSLATEREPO="translaterepo"
    }
    fun translateIndoToGayo(text: String, callback: Callback<TranslateResponse>) {
        val request = TranslateRequest(text)
        apiService.translateIndoToGayo(request).enqueue(object : Callback<TranslateResponse> {
            override fun onResponse(call: Call<TranslateResponse>, response: Response<TranslateResponse>) {
                if (response.isSuccessful) {
                    callback.onResponse(call, response)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody ?: "Failed to translate"
                    Log.e(TRANSLATEREPO, "Failed to translate:  $errorMessage")
                    callback.onFailure(call, Throwable("Failed to translate"))
                }
            }

            override fun onFailure(call: Call<TranslateResponse>, t: Throwable) {
                Log.e(TRANSLATEREPO, "Failed to translate", t)
                callback.onFailure(call, t)
            }
        })
    }

    fun translateGayoToIndo(text: String, callback: Callback<TranslateResponse>) {
        val request = TranslateRequest(text)
        apiService.translateGayoToIndo(request).enqueue(object : Callback<TranslateResponse> {
            override fun onResponse(call: Call<TranslateResponse>, response: Response<TranslateResponse>) {
                if (response.isSuccessful) {
                    callback.onResponse(call, response)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody ?: "Failed to translate"
                    Log.e(TRANSLATEREPO, "Failed to translate:  $errorMessage")
                    callback.onFailure(call, Throwable("Failed to translate"))
                }
            }

            override fun onFailure(call: Call<TranslateResponse>, t: Throwable) {
                Log.e(TRANSLATEREPO, "Failed to translate", t)
                callback.onFailure(call, t)
            }
        })
    }
    suspend fun addFavorite(favoriteEntity: FavoriteEntity) {
        favoriteDao.addFavorite(favoriteEntity)
    }

}
