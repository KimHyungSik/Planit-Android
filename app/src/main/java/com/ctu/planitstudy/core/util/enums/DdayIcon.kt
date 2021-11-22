package com.ctu.planitstudy.core.util.enums

import com.ctu.planitstudy.R


class DdayIconSet {
    enum class DdayIcon(val radio: Int) {
        PLANET(R.id.d_day_radio_button_1),
        PLANET_WITH_RINGS(R.id.d_day_radio_button_2),
        STAR(R.id.d_day_radio_button_3),
        FLAME(R.id.d_day_radio_button_4),
        UFO(R.id.d_day_radio_button_5)
    }

    val dDayIconList = listOf<String>(
        "PLANET",
        "PLANET_WITH_RINGS",
        "STAR",
        "FLAME",
        "UFO"
    )

    val dDayIconListId = listOf(
        R.id.d_day_radio_button_1,
        R.id.d_day_radio_button_2,
        R.id.d_day_radio_button_3,
        R.id.d_day_radio_button_4,
        R.id.d_day_radio_button_5
    )
}