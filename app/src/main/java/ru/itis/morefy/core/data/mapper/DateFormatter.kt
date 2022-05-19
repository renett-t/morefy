package ru.itis.morefy.core.data.mapper

import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class DateFormatter @Inject constructor() {
    fun getDate(time: String): Date {
        return SimpleDateFormat("dd-MM-yyy").parse(time)
    }
}
