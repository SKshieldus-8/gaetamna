package com.example.gtn

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RequestKey {
    @FormUrlEncoded
    @POST("/decryption")
    fun requestkey(
        @Field("access_token") access_token:String?
    ) : Call<dataKey>
}