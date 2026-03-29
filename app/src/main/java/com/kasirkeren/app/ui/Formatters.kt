package com.kasirkeren.app.ui

import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Int.toRupiah(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"))
    formatter.maximumFractionDigits = 0
    return formatter.format(this)
}

fun String.toDateGroupLabel(): String {
    val today = LocalDate.now()
    val date = LocalDate.parse(take(10), DateTimeFormatter.ISO_LOCAL_DATE)
    return when (date) {
        today -> "Hari Ini"
        today.minusDays(1) -> "Kemarin"
        else -> date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.forLanguageTag("id-ID")))
    }
}

fun String.toClockText(): String {
    return if (length >= 16) substring(11, 16) else this
}
