package com.example.gtn

import com.google.gson.annotations.SerializedName
import java.util.*

data class dataResponse (
    @SerializedName("result") val result: Int? = null,
    @SerializedName("tag") val tag: String? = null,
    @SerializedName("data") val data: List<Location>? = null,
    @SerializedName("msg") val msg: String? = null,
    @SerializedName("count") val count: Int? = null,
){
    data class Location (
        @SerializedName("x1") val x1: Int? = null,
        @SerializedName("y1") val y1: Int? = null,
        @SerializedName("x2") val x2: Int? = null,
        @SerializedName("y2") val y2: Int? = null,
    )
}