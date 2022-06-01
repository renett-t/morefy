package ru.itis.morefy.core.data.mapper

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class DateFormatter @Inject constructor() {
    fun getDate(time: String): Date {
        return try {
            SimpleDateFormat("dd-MM-yyy").parse(time)
        } catch (ex: ParseException) {
            try {
                SimpleDateFormat("yyyy").parse(time)
            } catch (ex: ParseException) {
                Log.e("DateFormatter ERROR.", "Unable to parse string '$time' to Date.")
                Date()
            }
        }

    }
}
