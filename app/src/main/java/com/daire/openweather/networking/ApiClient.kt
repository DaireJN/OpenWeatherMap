package com.daire.openweather.networking

import com.daire.openweather.networking.OpenWeatherMapConstants.Companion.BASE_URL
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


class ApiClient {

    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit!!
    }
}