package com.pedro.solutions.dialysisnotes.ui

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class Utils {

    companion object {
        const val DATE_FORMAT_DEFAULT = "dd/MM/yyyy hh:mm"
        const val DATE_FORMAT_DEFAULT_ONLY_DATE = "dd/MM/yyyy"

        const val MAX_OBSERVATIONS_CHARACTERS_SIZE = 10_000

        fun getDefaultLocale(context: Context): Locale {
            return context.resources.configuration.locales.get(0)
        }

        fun getDateAndTimeFromMillis(timeInMillis: Long, format: String, locale: Locale): String {
            if(timeInMillis == 0L) return ""
            return SimpleDateFormat(format, locale).apply { timeZone = TimeZone.getTimeZone("GMT") }.format(Date(timeInMillis))
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

        fun generatePDFFileName(context: Context, startInterval: Long, endInterval: Long): String {
            val start = getDateAndTimeFromMillis(startInterval, DATE_FORMAT_DEFAULT_ONLY_DATE, getDefaultLocale(context))
            val end = getDateAndTimeFromMillis(endInterval, DATE_FORMAT_DEFAULT_ONLY_DATE, getDefaultLocale(context))

            return "DialysisList-$start-$end.pdf"
        }
    }
}