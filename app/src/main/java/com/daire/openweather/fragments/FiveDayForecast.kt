package com.daire.openweather.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.daire.openweather.R
import com.daire.openweather.adapters.FiveDayForecastAdapter
import com.daire.openweather.models.ForecastDetail
import com.daire.openweather.models.ForecastResponse
import com.daire.openweather.networking.ApiClient
import com.daire.openweather.networking.ApiInterface
import com.daire.openweather.utilities.DateTimeHelper
import kotlinx.android.synthetic.main.fragment_five_day_forecast.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FiveDayForecast : Fragment() {

    /*
     * true when fragment is resumed from back stack
     * false when fragment is created
     */
    private var resumeFlag = false

    private var forecastDistinct: List<ForecastDetail>? = null
    private var savedForecastDesc: String? = ""
    private var savedForecastDate: String? = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // do not recreate fragment when resuming from back stack
        retainInstance = true
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_five_day_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        forecastRecycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        forecastRecycler.setHasFixedSize(true)

        // avoid unnecessary network requests
        if (!resumeFlag) {
            loadFiveDayForecast()
        } else {
            restoreForecastData()
        }

    }


    override fun onPause() {
        super.onPause()
        // save the strings to repopulate TextViews when fragment is resumed
        savedForecastDate = forecastDate?.text.toString()
        savedForecastDesc = weatherDescription?.text.toString()
        resumeFlag = true
    }

    override fun onResume() {
        Log.d("FiveDayForecast", "resume")
        super.onResume()
    }

    fun displayInitialForecast(forecastDistinct: List<ForecastDetail>?) {
        fiveDayProgress?.visibility = View.INVISIBLE
        cityTextView?.visibility = View.VISIBLE
        weatherDescription?.visibility = View.VISIBLE
        forecastDate?.visibility = View.VISIBLE
        forecastDate?.text = DateTimeHelper.formatDate(forecastDistinct?.get(0)?.date)
        weatherDescription?.text = forecastDistinct?.get(0)?.weather?.get(0)?.weatherDesc
    }

    private fun loadFiveDayForecast() {
        val apiService = ApiClient().getClient().create(ApiInterface::class.java)
        val call = apiService.getForecastData("belfast")
        call.enqueue(object : Callback<ForecastResponse> {
            override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {

                /*
                 * API returns multiple forecasts per day
                 * to simplify I will remove forecasts made on the same day
                 */
                forecastDistinct = response.body()?.forecast?.distinctBy { DateTimeHelper.formatDate(it.date) }

                displayInitialForecast(forecastDistinct)

                val adapterContext = context
                if (adapterContext != null) {
                    val forecastAdapter = FiveDayForecastAdapter(forecastDistinct, adapterContext)
                    forecastRecycler?.adapter = forecastAdapter
                    // register click listener for RecyclerView
                    registerClickListener(forecastAdapter)
                }
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                Log.d("FiveDayForecast", t.message)
            }
        })
    }

    private fun restoreForecastData() {
        val adapterContext = context
        if (adapterContext != null) {
            val forecastAdapter = FiveDayForecastAdapter(forecastDistinct, adapterContext)
            forecastRecycler?.adapter = forecastAdapter
            registerClickListener(forecastAdapter)
            displayInitialForecast(forecastDistinct)
        }
        forecastDate.text = savedForecastDate
        weatherDescription.text = savedForecastDesc
    }

    private fun registerClickListener(forecastAdapter: FiveDayForecastAdapter) {
        // register click listener for RecyclerView
        forecastAdapter.onItemClick = { forecastItem ->
            forecastDate?.text = DateTimeHelper.formatDate(forecastItem.date)
            weatherDescription?.text = forecastItem.weather[0].weatherDesc
        }
    }
}