package com.ctu.planitstudy.feature.presentation.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ctu.planitstudy.R
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyListDto

class TodoListRecyclerAdapter(val inTodoListRecycler: InTodoListRecycler) : RecyclerView.Adapter<TodoListRecyclerHolder>() {

    var studyList = StudyListDto(emptyList())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListRecyclerHolder =
        TodoListRecyclerHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.recycler_study_list_item, parent, false),
                inTodoListRecycler
        )

    override fun onBindViewHolder(holder: TodoListRecyclerHolder, position: Int) {
       holder.bindWithView(
           this.studyList.studies[position],
       )
    }

    override fun getItemCount(): Int = studyList.studies.size

    fun submitList(studyList : StudyListDto){
        this.studyList = studyList
    }
}