package com.ctu.planitstudy.core.util.enums

import com.ctu.planitstudy.R

class DdayIconSet {
    enum class DdayIcon(val radio: Int) {
        FULLMOON(R.id.d_day_radio_button_1),
        SATURN(R.id.d_day_radio_button_2),
        STAR(R.id.d_day_radio_button_3),
        METEOR(R.id.d_day_radio_button_4),
        UFO(R.id.d_day_radio_button_5)
    }

    enum class DdayIconImg(val imge: Int) {
        FULLMOON(R.drawable.ic_fullmoon),
        SATURN(R.drawable.icon_32_saturn),
        STAR(R.drawable.icon_32_star),
        METEOR(R.drawable.icon_32_meteor),
        UFO(R.drawable.icon_32_ufo)
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
