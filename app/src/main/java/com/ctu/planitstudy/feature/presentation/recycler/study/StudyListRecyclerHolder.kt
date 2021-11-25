package com.ctu.planitstudy.feature.presentation.recycler.study

import android.annotation.SuppressLint
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.util.enums.weekEngList
import com.ctu.planitstudy.core.util.enums.weekKorList
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyDto

class StudyListRecyclerHolder(itemView: View, val inTodoListRecycler: InStudyListRecycler) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val titleText = itemView.findViewById<TextView>(R.id.study_list_title_text)
    private val stateText = itemView.findViewById<TextView>(R.id.study_list_state_text)
    private val checkBox = itemView.findViewById<CheckBox>(R.id.study_list_check_box)
    private val view = itemView.findViewById<CardView>(R.id.study_list_view)

    init {
        view.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        inTodoListRecycler.onClickedItem(bindingAdapterPosition)
    }

    @SuppressLint("ResourceAsColor")
    fun bindWithView(studyDto: StudyDto, studyListModel: StudyListMode) {
        titleText.text = studyDto.title

        when (studyListModel) {
            is StudyListMode.HomeStudyListMode -> {
                checkBox.visibility = View.GONE
                stateText.text = "측정된 공부시간이 없습니다"
            }
            is StudyListMode.PlannerStudyListMode -> {
                checkBox.visibility = View.VISIBLE
                checkBox.isChecked = studyDto.isDone

                val weekString = buildString {
                    append("${studyDto.startAt}~${studyDto.endAt} | ")
                    if (studyDto.repeatedDays != null) {
                        for (n in studyDto.repeatedDays) {
                            append(weekKorList[weekEngList.indexOf(n)])
                        }
                        append("반복")
//                        studyDay += weekString + "반복"
                    }
                }

                if (studyDto.startAt != studyDto.endAt) {
                    stateText.visibility = View.VISIBLE
                    stateText.text = weekString
                } else
                    stateText.visibility = View.GONE
            }
        }
    }
}
