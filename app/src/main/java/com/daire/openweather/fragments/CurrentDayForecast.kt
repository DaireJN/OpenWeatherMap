package com.daire.openweather.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daire.openweather.R
import com.daire.openweather.models.CurrentWeatherResponse
import com.daire.openweather.networking.ApiClient
import com.daire.openweather.networking.ApiInterface
import com.daire.openweather.utilities.DateTimeHelper
import kotlinx.android.synthetic.main.fragment_current_day_forecast.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CurrentDayForecast : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // do not recreate fragment when resuming from back stack
        retainInstance = true
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_day_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // avoid unnecessary network requests
        if (savedInstanceState == null) {
            loadWeatherToday()
        } else {
            restoreWeatherData(savedInstanceState)
            currentDayProgress?.visibility = View.INVISIBLE
        }

    }

    private fun loadWeatherToday() {
        val apiService = ApiClient().getClient().create(ApiInterface::class.java)

        val call = apiService.getCityData("belfast")
        call.enqueue(object : Callback<CurrentWeatherResponse> {
            override fun onResponse(call: Call<CurrentWeatherResponse>, response: Response<CurrentWeatherResponse>) {
                currentDayProgress?.visibility = View.INVISIBLE
                displayForecastInfo(response.body())
            }

            override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {
                displayForecastError()
                Log.d("CurrentDayForecast", t.message)
            }
        })
    }

    private fun displayForecastInfo(weatherToday: CurrentWeatherResponse?) {

        currentDay?.text = DateTimeHelper.getCurrentDay()
        currentDate?.text = DateTimeHelper.getCurrentDate(weatherToday?.sunInfo?.SunsetTime)

        currentTemperature?.text = getString(R.string.current_temp, weatherToday?.mainDetails?.currentTemperature)
        currentMinTemp?.text = getString(R.string.min_temp, weatherToday?.mainDetails?.minTemp.toString())
        currentMaxTemp?.text = getString(R.string.max_temp, weatherToday?.mainDetails?.maxTemp.toString())
        currentSunrise?.text = getString(R.string.sunrise, DateTimeHelper.formatTime(weatherToday?.sunInfo?.sunriseTime))
        currentSunset?.text = getString(R.string.sunset, DateTimeHelper.formatTime(weatherToday?.sunInfo?.SunsetTime))
    }

    private fun displayForecastError() {
        currentDayError?.visibility = View.VISIBLE
        currentDayProgress?.visibility = View.INVISIBLE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("DAY", currentDay?.text.toString())
        outState.putString("TEMPERATURE", currentTemperature?.text.toString())
        outState.putString("MIN_TEMP", currentMinTemp?.text.toString())
        outState.putString("MAX_TEMP", currentMaxTemp?.text.toString())
        outState.putString("SUNRISE", currentSunrise?.text.toString())
        outState.putString("SUNSET", currentSunset?.text.toString())
        outState.putString("DATE", currentDate?.text.toString())
    }

    private fun restoreWeatherData(savedInstanceState: Bundle?) {
        currentDay?.text = savedInstanceState?.getString("DAY")
        currentTemperature?.text = savedInstanceState?.getString("TEMPERATURE")
        currentMinTemp?.text = savedInstanceState?.getString("MAX_TEMP")
        currentMaxTemp?.text = savedInstanceState?.getString("MIN_TEMP")
        currentSunrise?.text = savedInstanceState?.getString("SUNRISE")
        currentSunset?.text = savedInstanceState?.getString("SUNSET")
        currentDate?.text = savedInstanceState?.getString("DATE")
        // fall back to reload data if restore fails
        if (savedInstanceState?.getString("DAY").equals("null")) {
            loadWeatherToday()
        }
    }
}
