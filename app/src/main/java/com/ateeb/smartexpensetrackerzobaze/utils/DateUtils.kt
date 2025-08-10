package com.ateeb.smartexpensetrackerzobaze.utils

import java.util.Calendar
import java.util.Date

object DateUtils {

    fun getTodayStartAndEndDates(): Pair<Date, Date> {
        val calendar = Calendar.getInstance()
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startOfDay = calendar.time

        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val endOfDay = calendar.time

        return Pair(startOfDay, endOfDay)
    }

    fun getStartAndEndDatesFor(date: Date): Pair<Date, Date> {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startOfDay = calendar.time

        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val endOfDay = calendar.time

        return Pair(startOfDay, endOfDay)
    }

}