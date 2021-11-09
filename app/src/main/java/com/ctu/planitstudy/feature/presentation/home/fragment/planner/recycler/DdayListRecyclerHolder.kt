package com.ctu.planitstudy.feature.presentation.home.fragment.planner.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ctu.planitstudy.R
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayListDto
import com.ctu.planitstudy.feature.presentation.home.fragment.home.recycler.InTodoListRecycler

class DdayListRecyclerHolder(itemView: View, inDdayListRecycler: InDdayListRecycler) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val titleText = itemView.findViewById<TextView>(R.id.planner_d_day_recycler_representative_title)
    private val dDayText = itemView.findViewById<TextView>(R.id.planner_d_day_recycler_representative_d_day)
    private val dateText = itemView.findViewById<TextView>(R.id.planner_d_day_recycler_representative_date)


    override fun onClick(v: View?) {
    }

    fun bindWithView(dday : DdayDto){
        titleText.text = dday.title
        dDayText.text =  "D-" + dday.dDay
        dateText.text = dday.endAt
    }
}