package com.ctu.planitstudy.feature.presentation.recycler.study

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ctu.planitstudy.R
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyListDto

class StudyListRecyclerAdapter(val inTodoListRecycler: InStudyListRecycler) : RecyclerView.Adapter<StudyListRecyclerHolder>() {

    var studyList = StudyListDto(emptyList())
    var studyListMode : StudyListMode = StudyListMode.HomeStudyListMode

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyListRecyclerHolder =
        StudyListRecyclerHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.recycler_study_list_item, parent, false),
                inTodoListRecycler
        )

    override fun onBindViewHolder(holder: StudyListRecyclerHolder, position: Int) {
       holder.bindWithView(
           this.studyList.studies[position],
           studyListMode
       )
    }

    override fun getItemCount(): Int = studyList.studies.size

    fun submitList(studyList : StudyListDto, studyListModel : StudyListMode){
        this.studyList = studyList
        this.studyListMode = studyListModel
    }
}