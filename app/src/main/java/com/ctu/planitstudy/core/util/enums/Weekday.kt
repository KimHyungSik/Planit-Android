package com.ctu.planitstudy.core.util.enums

enum class Weekday(val weekKor: String, val weekEng: String) {
    All("매일", "All"),
    Sunday("일", "SUNDAY"),
    Monday("월", "MONDAY"),
    Tuesday("화", "TUESDAY"),
    Wednesday("수", "WEDNESDAY"),
    Thursday("목", "THURSDAY"),
    Friday("금", "FRIDAY"),
    Saturday("토", "SATURDAY")
}

val weekKorList: List<String> = listOf(
    "매일",
    "일",
    "월",
    "화",
    "수",
    "목",
    "금",
    "토"
)

val weekEngList: List<String> = listOf(
    "All",
    "SUNDAY",
    "MONDAY",
    "TUESDAY",
    "WEDNESDAY",
    "THURSDAY",
    "FRIDAY",
    "SATURDAY"
)