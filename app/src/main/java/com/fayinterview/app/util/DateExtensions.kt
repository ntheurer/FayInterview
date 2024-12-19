package com.fayinterview.app.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class DatePattern(val value: String) {
    // e.g. 5:01 PM
    TIME("h:mm a"),

    // e.g. Dec
    MONTH("MMM"),

    // e.g. 18
    DAY("d")
}

fun Date.humanReadable(pattern: DatePattern): String {
    return SimpleDateFormat(pattern.value, Locale.getDefault()).format(this)
}

fun Date.isPast(): Boolean {
    return this.before(Date())
}
