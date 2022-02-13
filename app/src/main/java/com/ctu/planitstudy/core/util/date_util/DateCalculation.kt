package com.ctu.planitstudy.core.util.date_util

import com.ctu.planitstudy.core.util.enums.Weekday
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.collections.ArrayList
import kotlin.math.min

class DateCalculation {

    val TAG = "DateCalculation - 로그"
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    val cal = Calendar.getInstance()

    fun calDateBetween(startDate: String, endDate: String): Int {
        val start = dateFormat.parse(startDate)
        val end = dateFormat.parse(endDate)
        val calDate: Long = end!!.time - start!!.time
        val calDay: Long = calDate / (24 * 60 * 60 * 1000)
        return calDay.toInt()
    }

    // addDate 현재 날짜에서 날짜 추가₩
    fun getCurrentDateString(addDate: Int?): String {
        // 현재시간을 가져오기
        val now = System.currentTimeMillis()

        // 현재 시간을 Date 타입으로 변환
        val t_date = Date(now)

        cal.time = t_date
        if (addDate != null)
            cal.add(Calendar.DATE, addDate)

        // 현재 시간을 dateFormat 에 선언한 형태의 String 으로 변환
        val str_date = dateFormat.format(cal.time)

        return str_date
    }

    fun calDateBetweenWeek(startDate: String, endDate: String): ArrayList<Weekday> {
        // 활성화 될 요일 리스트
        val activationWeek = ArrayList<Weekday>()

        // 종료 날짜가 시작날짜 이전 이라면
        if (endDate < startDate) return activationWeek

        // 현재 계산 중인 날짜
        val current = dateFormat.parse(startDate)

        cal.time = current!!
        activationWeek.add(Weekday.values()[cal.get(Calendar.DAY_OF_WEEK)])
        val dateDifferene = calDateBetween(startDate, endDate)

        // 최대 일주일 동안 활성화될 요일 검사
        for (i in 0 until min(dateDifferene, 6)) {
            cal.add(Calendar.DATE, 1)
            activationWeek.add(Weekday.values()[cal.get(Calendar.DAY_OF_WEEK)])
        }

        activationWeek.sortBy { it.ordinal }

        return activationWeek
    }
}
