package com.khairililmi.speakgayo.data.remote

import com.google.gson.annotations.SerializedName

data class TranslateResponse(
    @SerializedName("translation")
    val translatedText: String
)
