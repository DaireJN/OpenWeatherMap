package com.daire.openweather.models

import com.google.gson.annotations.SerializedName

data class Sys(@SerializedName("sunrise") var sunriseTime: Long,
               @SerializedName("sunset") var SunsetTime: Long,
               @SerializedName("pod") var pod: String,
               @SerializedName("dt_txt") var dateText: String
)
