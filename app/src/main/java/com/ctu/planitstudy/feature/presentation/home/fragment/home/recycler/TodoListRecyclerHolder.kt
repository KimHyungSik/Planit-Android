package com.ctu.planitstudy.feature.presentation.home.fragment.home.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ctu.planitstudy.R

class TodoListRecyclerHolder(itemView: View, inTodoListRecycler: InTodoListRecycler) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val titleText = itemView.findViewById<TextView>(R.id.home_todo_list_item_title)
    private val stateText = itemView.findViewById<TextView>(R.id.home_todo_list_item_state)

    override fun onClick(v: View?) {
    }

    fun bindWithView(title: String, state : String){
        titleText.text = title
        stateText.text= state
    }
}