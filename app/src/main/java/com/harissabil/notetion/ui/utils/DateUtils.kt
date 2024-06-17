package com.harissabil.notetion.ui.utils

import kotlinx.datetime.Instant
import timber.log.Timber
import java.text.DateFormat
import java.util.Date
import java.util.Locale

fun Instant.localiseDate(): String {
    val dateString: String
    try {
        // Convert Instant to java.util.Date
        val date = Date(this.toEpochMilliseconds())
        val dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault())
        dateString = dateFormat.format(date)
    } catch (e: Exception) {
        Timber.tag("DATE").e(e, "Error formatting date")
        e.printStackTrace()
        return this.toString()
    }

    return dateString
}
