package com.ctu.planitstudy.feature.presentation.home.fragment.planner.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ctu.planitstudy.R
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayListDto

class DdayListRecyclerAdapter(val inDdayListRecycler: InDdayListRecycler) : RecyclerView.Adapter<DdayListRecyclerHolder>() {

    var dDayList = ArrayList<DdayDto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DdayListRecyclerHolder =
       DdayListRecyclerHolder(
           LayoutInflater
               .from(parent.context)
               .inflate(R.layout.recycler_planner_d_day_item, parent, false),
            inDdayListRecycler
       )


    override fun onBindViewHolder(holder: DdayListRecyclerHolder, position: Int) {
        holder.bindWithView(
            this.dDayList[position]
        )
    }

    override fun getItemCount(): Int = dDayList.size

    fun submitList(ddays : ArrayList<DdayDto>){
        this.dDayList = ddays
    }
}