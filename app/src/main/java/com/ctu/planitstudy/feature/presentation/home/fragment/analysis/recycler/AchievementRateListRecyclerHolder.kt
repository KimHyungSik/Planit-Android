package com.ctu.planitstudy.feature.presentation.home.fragment.analysis.recycler

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.util.longToTimeShortString
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyDto

class AchievementRateListRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val title: TextView = itemView.findViewById<TextView>(R.id.achievement_rate_title_text)
    val time: TextView = itemView.findViewById(R.id.achievement_rate_time_text)
    val topLine: View = itemView.findViewById(R.id.achievement_rate_top_line)
    val bottomLine: View = itemView.findViewById(R.id.achievement_rate_bottom_line)

    @SuppressLint("ResourceAsColor")
    fun bindWithView(studyDto: StudyDto, top: Boolean, bottom: Boolean) {
        title.text = studyDto.title
        time.text = studyDto.recordedTime.toLong().longToTimeShortString()

        if (top)
            topLine.visibility = View.INVISIBLE
        else
            topLine.visibility = View.VISIBLE
        if (bottom)
            bottomLine.visibility = View.INVISIBLE
        else
            bottomLine.visibility = View.VISIBLE
    }
}
