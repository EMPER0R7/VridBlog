package com.example.bloger.util

import java.text.SimpleDateFormat
import java.util.*

fun String.formatToReadableDate(): String {
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val date = parser.parse(this)
        formatter.format(date ?: return this)
    } catch (e: Exception) {
        this // Return original string if parsing fails
    }
}
