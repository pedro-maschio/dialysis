package com.pedro.solutions.dialysisnotes

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Utils {

    companion object {
        val LOCALE_DEFAULT = Locale.US
        const val DATE_FORMAT_DEFAULT = "dd/MM/yy hh:mm"
        fun getDateAndTimeFromMillis(timeInMillis: Long, format: String, locale: Locale): String {
            return SimpleDateFormat(format, locale).format(Date(timeInMillis))
        }
    }
}