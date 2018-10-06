package com.daire.openweather.models

import com.google.gson.annotations.SerializedName

data class ForecastDetail(@SerializedName("dt") var date: Long,
                          @SerializedName("main") var mainDetails: Main,
                          @SerializedName("wind") var windDetails: Wind,
                          @SerializedName("weather") var weather: List<Weather>
)