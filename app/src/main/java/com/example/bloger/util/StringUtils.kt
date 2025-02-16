package com.example.bloger.util

import android.text.Html

fun String.removeHtmlTags(): String {
    return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString().trim()
}
