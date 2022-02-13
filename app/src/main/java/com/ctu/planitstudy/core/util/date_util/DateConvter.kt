package com.ctu.planitstudy.core.util.date_util

import com.ctu.planitstudy.core.util.enums.Weekday
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateConvter {

    val TAG = "DateConvter - 로그"

    private val calendar = Calendar.getInstance()
    private val dateFormatDdayPoint = SimpleDateFormat("yy.MM.dd", Locale.KOREA)
    private val dateFormatDdayDto = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    private val dateFormatText = SimpleDateFormat("yyyy년MM월dd일", Locale.KOREA)

    fun dtoDateTOLong(date: String): Long {
        return dateFormatDdayDto.parse(date)!!.time
    }

    fun textDateToLongDate(date: String): Long {
        val sliceDate = date.slice(IntRange(0, 10))
        return dateFormatText.parse(sliceDate)!!.time
    }

    // yyyy-MM-dd 에서 yyyy년MM월dd일(요일) 로 변경
    // null의 경우 현재 날짜로 출력
    fun dtoDateToTextDate(date: String?): String {
        return if (date != null) {
            calendar.time = dateFormatDdayDto.parse(date)!!
            dateFormatText.format(dateFormatDdayDto.parse(date)!!) + "(${
            Weekday.values()[calendar.get(Calendar.DAY_OF_WEEK)].weekKor
            })"
        } else {
            calendar.time =
                dateFormatDdayDto.parse(dateFormatDdayDto.format(System.currentTimeMillis()))!!
            dateFormatText.format(System.currentTimeMillis()) + "(${
            Weekday.values()[calendar.get(Calendar.DAY_OF_WEEK)].weekKor
            })"
        }
    }
    // yyyy-MM-dd 에서 yy.MM.dd일 로 변경
    fun dtoDateToPointDate(date: String?): String {
        return if (date != null) {
            dateFormatDdayPoint.format(dateFormatDdayDto.parse(date)!!)
        } else {
            dateFormatDdayPoint.format(System.currentTimeMillis())
        }
    }

    // yyyy년MM월dd일(요일) 에서 yyyy-MM-dd 로 변경
    // null의 경우 현재 날짜로 출력
    fun textDateToDtoDate(date: String?): String {
        return if (date != null) {
            val sliceDate = date.slice(IntRange(0, 10))
            calendar.time = dateFormatText.parse(sliceDate)!!
            dateFormatDdayDto.format(dateFormatText.parse(sliceDate)!!)
        } else {
            calendar.time =
                dateFormatText.parse(dateFormatText.format(System.currentTimeMillis()))!!
            dateFormatDdayDto.format(System.currentTimeMillis())
        }
    }
}
