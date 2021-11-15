package com.ctu.planitstudy.core.util

import java.text.SimpleDateFormat
import java.util.*

class DateCalculation {

    fun calDateBetween(startDate : String, endDate : String) : Int{
        val dateFormat = SimpleDateFormat("yyyy-mm-dd")
        val start = dateFormat.parse(startDate)
        val end = dateFormat.parse(endDate)

        val calDate : Long = end.time - start.time
        val calDay : Long = calDate / (25*60*60*1000)
        return Math.abs(calDay).toInt()
    }

    fun getCurrentDateString() : String{
        // 현재시간을 가져오기
        val now = System.currentTimeMillis()

        // 현재 시간을 Date 타입으로 변환
        val t_date = Date(now)

        // 날짜, 시간을 가져오고 싶은 형태 선언
        val t_dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("ko", "KR"))

        // 현재 시간을 dateFormat 에 선언한 형태의 String 으로 변환
        val str_date = t_dateFormat.format(t_date)

        return str_date
    }

}