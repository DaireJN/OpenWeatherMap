package com.daire.openweather.models

import com.google.gson.annotations.SerializedName

data class Weather(
        @SerializedName("id") var weatherID: String,
        @SerializedName("main") var weatherMainDec: String,
        @SerializedName("description") var weatherDesc: String,
        @SerializedName("icon") var weatherIcon: String
)