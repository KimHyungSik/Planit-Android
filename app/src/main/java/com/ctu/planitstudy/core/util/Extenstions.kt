package com.ctu.planitstudy.core.util

import androidx.core.content.ContextCompat
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import java.time.DayOfWeek
import java.time.temporal.WeekFields
import java.util.*

fun String?.isJsonObject(): Boolean = this?.startsWith("{") == true && this.endsWith("}")
fun String?.isJsonArray(): Boolean = this?.startsWith("[") == true && this.endsWith("]")
fun String.isValidText(): Boolean = isValidString(this)

fun isValidString(text: String): Boolean {
    val exp = Regex(pattern = "^[가-힣ㄱ-ㅎa-zA-Z0-9^\\S]+\$")
    return text.isNotEmpty() && exp.matches(text)
}

fun daysOfWeekFromLocale(): Array<DayOfWeek> {
    val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
    var daysOfWeek = DayOfWeek.values()
    // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
    // Only necessary if firstDayOfWeek != DayOfWeek.MONDAY which has ordinal 0.
    if (firstDayOfWeek != DayOfWeek.MONDAY) {
        val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
        val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
        daysOfWeek = rhs + lhs
    }
    return daysOfWeek
}

fun Long.longToTimeString(): String = longToTimerString(this)
fun Long.longToTimeShortString(): String = longToTimerShortString(this)
fun Long.longToTimeKorString(): String = longToTimerKorString(this)

fun longToTimerString(time: Long): String {
    var currentTime = time
    val h = time / 3600
    currentTime = time % 3600
    val m = currentTime / 60
    currentTime %= 60
    val s = currentTime
    val string = buildString {
        if (h < 10)
            append(0)
        append(h)
        append(":")
        if (m < 10)
            append(0)
        append(m)
        append(":")
        if (s < 10)
            append(0)
        append(s)
    }
    return string
}

fun longToTimerShortString(time: Long): String {
    var currentTime = time
    val h = time / 3600
    currentTime = time % 3600
    val m = currentTime / 60
    currentTime %= 60
    val s = currentTime
    val string = buildString {
        if (h != 0L) {
            if (h < 10)
                append(0)
            append(h)
            append(":")
        }

        if (m != 0L) {
            if (m < 10)
                append(0)
            append(m)
            append(":")
        }

        if (s < 10)
            append(0)
        append(s)
        append("초")
    }
    return string
}

fun longToTimerKorString(time: Long): String {
    var currentTime = time
    val h = time / 3600
    currentTime = time % 3600
    val m = currentTime / 60
    currentTime %= 60
    val s = currentTime
    val string = buildString {
        if (h != 0L) {
            if (h < 10)
                append(0)
            append(h)
            append("시간")
        }

        if (m != 0L) {
            if (m < 10)
                append(0)
            append(m)
            append("분")
        }

        if (s < 10)
            append(0)
        append(s)
        append("초")
    }
    return string
}

fun setColor(color: Int) = ContextCompat.getColor(CashStudyApp.instance, color)
