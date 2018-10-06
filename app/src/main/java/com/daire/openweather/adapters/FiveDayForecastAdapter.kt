package com.daire.openweather.adapters

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.daire.openweather.R
import com.daire.openweather.models.ForecastDetail
import com.daire.openweather.utilities.DateTimeHelper
import kotlinx.android.synthetic.main.five_day_forecast_item.view.*


class FiveDayForecastAdapter(private val forecastDetails: List<ForecastDetail>?, private val context: Context) : RecyclerView.Adapter<FiveDayForecastAdapter.ForecastViewHolder>() {

    var onItemClick: ((ForecastDetail) -> Unit)? = null
    private var iconBaseUrl = "http://openweathermap.org/img/w/"
    private val resources: Resources? = context.resources

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        if (forecastDetails != null && resources != null) {
            with(holder) {
                val forecastItem = forecastDetails[position]
                view.tag = position
                view.fiveDayDate.text = DateTimeHelper.formatDate(forecastItem.date)
                view.fiveDayMaxTemp.text = resources.getString(R.string.max_temp, forecastItem.mainDetails.maxTemp.toString())
                view.fiveDayMinTemp.text = resources.getString(R.string.min_temp, forecastItem.mainDetails.minTemp.toString())
                view.fiveDayWindSpeed.text = resources.getString(R.string.wind_speed, forecastItem.windDetails.windSpeed.toString())
                view.fiveDayWindDirection.text = resources.getString(R.string.wind_direction, forecastItem.windDetails.windDegree.toString())
                val iconUrl = iconBaseUrl + forecastItem.weather[0].weatherIcon + ".png"
                Glide
                        .with(context)
                        .load(iconUrl)
                        .apply(RequestOptions().override(30, 30))
                        .into(view.weatherIcon)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.five_day_forecast_item, parent, false)
        return ForecastViewHolder(view)
    }

    override fun getItemCount(): Int {
        return when (forecastDetails != null) {
            true -> forecastDetails!!.size
            false -> 0
        }
    }

    inner class ForecastViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                if (forecastDetails != null) {
                    onItemClick?.invoke(forecastDetails[adapterPosition])
                }
            }
        }
    }
}

