package com.example.gtn

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface RequestInformation {
    @Multipart
    @POST("ocr")
    fun requestInformation(
        @Part file: MultipartBody.Part?
    ): Call<dataResponse>
}
