package ru.zakoulov.githubkotlintrends

import java.util.*

private val suffixes: NavigableMap<Int, String> = TreeMap<Int, String>().apply {
    put(1_000, "k")
    put(1_000_000, "M")
    put(1_000_000_000, "G")
}

fun Int.formatToShort(): String {
    val value = this
    if (value < 1000) return value.toString() //deal with easy case
    val e = suffixes.floorEntry(value) as Map.Entry<Int, String>
    val divideBy = e.key
    val suffix = e.value
    val truncated = value / (divideBy / 10) //the number part of the output times 10
    val hasDecimal = truncated < 100 && truncated / 10.0 != (truncated / 10).toDouble()
    return if (hasDecimal) (truncated / 10.0).toString() + suffix else (truncated / 10).toString() + suffix
}
