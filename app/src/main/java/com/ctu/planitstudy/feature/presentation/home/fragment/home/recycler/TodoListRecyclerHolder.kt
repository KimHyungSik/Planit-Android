package com.ctu.planitstudy.feature.presentation.home.fragment.home.recycler

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ctu.planitstudy.R
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyDto

class TodoListRecyclerHolder(itemView: View, inTodoListRecycler: InTodoListRecycler) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val titleText = itemView.findViewById<TextView>(R.id.home_todo_list_item_title)
    private val stateText = itemView.findViewById<TextView>(R.id.home_todo_list_item_state)

    override fun onClick(v: View?) {
    }

    @SuppressLint("ResourceAsColor")
    fun bindWithView(studyDto: StudyDto){
        titleText.text = studyDto.title
        stateText.text = "측정된 공부시간이 없습니다"
        stateText.setTextColor(R.color.text_color)
    }
}