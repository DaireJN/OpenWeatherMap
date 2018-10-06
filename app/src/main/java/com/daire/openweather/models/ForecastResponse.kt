package com.daire.openweather.models

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
        @SerializedName("list") var forecast: List<ForecastDetail>

)