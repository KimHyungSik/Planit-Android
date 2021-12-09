package com.ctu.planitstudy.feature.presentation.home.fragment.analysis.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ctu.planitstudy.R
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyListDto
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyTimeLineDto

class AchievementRateListRecyclerAdapter() : RecyclerView.Adapter<AchievementRateListRecyclerHolder>() {

    var studyTimeLine = StudyTimeLineDto(0, emptyList(), 0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementRateListRecyclerHolder =
        AchievementRateListRecyclerHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.recycler_achievement_rate_item, parent, false)
        )

    override fun onBindViewHolder(holder: AchievementRateListRecyclerHolder, position: Int) {
        holder.bindWithView(
            studyTimeLine = this.studyTimeLine.reports[position],
            top = position == 0,
            bottom = position == this.studyTimeLine.reports.size - 1
        )
    }

    override fun getItemCount(): Int = studyTimeLine.reports.size

    fun submitList(studyTimeLine: StudyTimeLineDto) {
        this.studyTimeLine = studyTimeLine
    }
}
