package com.pedro.solutions.dialysisnotes.ui

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Utils {

    companion object {
        const val DATE_FORMAT_DEFAULT = "dd/MM/yy hh:mm"

        const val MAX_OBSERVATIONS_CHARACTERS_SIZE = 10_000

        fun getDefaultLocale(context: Context): Locale {
            return context.resources.configuration.locales.get(0)
        }

        fun getDateAndTimeFromMillis(timeInMillis: Long, format: String, locale: Locale): String {
            return SimpleDateFormat(format, locale).format(Date(timeInMillis))
        }

        fun isStringInt(num: String): Boolean {
            return when (num.toIntOrNull()) {
                null -> false
                else -> true
            }
        }

        private fun getMonthFromMillis(timeInMillis: Long): Int {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timeInMillis
            return calendar.get(Calendar.MONTH)
        }

        fun getMonthNameFromInteger(timeInMillis: Long, locale: Locale): String {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.MONTH, getMonthFromMillis(timeInMillis))
            return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT, locale)!!
                .replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(locale) else it.toString()
                }
        }
    }
}