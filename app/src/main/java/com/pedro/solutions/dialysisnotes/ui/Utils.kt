package com.pedro.solutions.dialysisnotes.ui

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Utils {

    companion object {
        val LOCALE_DEFAULT: Locale = Locale.US
        const val DATE_FORMAT_DEFAULT = "dd/MM/yy hh:mm"

        const val MAX_OBSERVATIONS_CHARACTERS_SIZE = 10_000
        fun getDateAndTimeFromMillis(timeInMillis: Long, format: String, locale: Locale): String {
            return SimpleDateFormat(format, locale).format(Date(timeInMillis))
        }

        fun isStringInt(num: String): Boolean {
            return when(num.toIntOrNull()) {
                null -> false
                else -> true
            }
        }
    }
}