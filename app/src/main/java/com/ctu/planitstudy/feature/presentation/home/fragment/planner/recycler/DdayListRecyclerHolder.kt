package com.ctu.planitstudy.feature.presentation.home.fragment.planner.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.util.date_util.DateConvter
import com.ctu.planitstudy.core.util.enums.DdayIconSet
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.google.android.material.card.MaterialCardView

class DdayListRecyclerHolder(itemView: View, val inDdayListRecycler: InDdayListRecycler) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val view = itemView.findViewById<MaterialCardView>(R.id.planner_d_day_recycler_item_view)
    private val titleText = itemView.findViewById<TextView>(R.id.planner_d_day_recycler_representative_title)
    private val dDayText = itemView.findViewById<TextView>(R.id.planner_d_day_recycler_representative_d_day)
    private val dateText = itemView.findViewById<TextView>(R.id.planner_d_day_recycler_representative_date)
    private val imageView = itemView.findViewById<ImageView>(R.id.planner_d_day_recycler_icon)

    init {
        this.view.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        inDdayListRecycler.onClickedItem(adapterPosition)
    }

    fun bindWithView(dday: DdayDto) {
        titleText.text = dday.title
        dDayText.text = if (dday.dDay.toInt() >= 0) "D-${dday.dDay}" else "D+${Math.abs(dday.dDay)}"
        dateText.text = DateConvter.dtoDateToPointDate(dday.endAt)
        imageView.setImageResource(DdayIconSet.DdayIconImg.values()[DdayIconSet().dDayIconList.indexOf(dday.icon)].imge)
    }
}
