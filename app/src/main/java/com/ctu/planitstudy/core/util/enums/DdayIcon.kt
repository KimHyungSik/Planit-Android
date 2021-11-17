package com.ctu.planitstudy.core.util.enums

import com.ctu.planitstudy.R


class DdayIconSet {
    enum class DdayIcon(val radio: Int) {
        PINK(R.id.d_day_radio_button_1),
        GREEN(R.id.d_day_radio_button_2),
        YELLOW(R.id.d_day_radio_button_3),
        LIGHT_BLUE(R.id.d_day_radio_button_4),
        DARK_BLUE(R.id.d_day_radio_button_5)
    }

    val dDayIconList = listOf<String>(
        "PINK",
        "GREEN",
        "YELLOW",
        "LIGHT_BLUE",
        "DARK_BLUE"
    )

    val dDayIconListId = listOf(
        R.id.d_day_radio_button_1,
        R.id.d_day_radio_button_2,
        R.id.d_day_radio_button_3,
        R.id.d_day_radio_button_4,
        R.id.d_day_radio_button_5
    )
}