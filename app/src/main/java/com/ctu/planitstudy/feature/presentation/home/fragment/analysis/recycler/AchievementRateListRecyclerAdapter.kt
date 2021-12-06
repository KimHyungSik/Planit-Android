package com.ctu.planitstudy.feature.presentation.home.fragment.analysis.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ctu.planitstudy.R
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyListDto

class AchievementRateListRecyclerAdapter() : RecyclerView.Adapter<AchievementRateListRecyclerHolder>() {

    var studyList = StudyListDto("", emptyList())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementRateListRecyclerHolder =
        AchievementRateListRecyclerHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.recycler_achievement_rate_item, parent, false)
        )

    override fun onBindViewHolder(holder: AchievementRateListRecyclerHolder, position: Int) {
        holder.bindWithView(
            studyDto =  this.studyList.studies[position],
            top = position == 0,
            bottom = position == this.studyList.studies.size - 1
        )
    }

    override fun getItemCount(): Int = studyList.studies.size

    fun submitList(studyList: StudyListDto) {
        this.studyList = studyList
    }
}
