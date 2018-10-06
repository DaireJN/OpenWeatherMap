package com.daire.openweather.utilities

import java.text.SimpleDateFormat
import java.util.*

class DateTimeHelper {

    companion object {
        fun getCurrentDate(timeIn: Long?): String {
            return if (timeIn != null) {
                // convert unix epoch into milliseconds
                val time = Date(timeIn * 1000)
                val formattedTime = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(time)
                formattedTime
            } else {
                "Date not Available"
            }
        }

        fun formatTime(timeIn: Long?): String {
            return if (timeIn != null) {
                val time = Date(timeIn * 1000)
                val formattedTime = SimpleDateFormat("h:mm a", Locale.ENGLISH).format(time)
                formattedTime
            } else {
                "Time not Available"
            }
        }

        fun getCurrentDay(): String {
            return SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis())
        }

        fun formatDate(dateIn: Long?): String {
            return if (dateIn != null) {
                val time = Date(dateIn * 1000)
                val formattedTime = SimpleDateFormat("dd/MM/yy", Locale.ENGLISH).format(time)
                formattedTime
            } else {
                "Date not Available"
            }
        }
    }
}