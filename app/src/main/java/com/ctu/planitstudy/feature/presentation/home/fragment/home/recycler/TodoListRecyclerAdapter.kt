package com.ctu.planitstudy.feature.presentation.home.fragment.home.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ctu.planitstudy.R

class TodoListRecyclerAdapter(val inTodoListRecycler: InTodoListRecycler) : RecyclerView.Adapter<TodoListRecyclerHolder>() {

    var titleList = ArrayList<String>()
    var stateList = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListRecyclerHolder =
        TodoListRecyclerHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.recycler_home_todo_item, parent, false),
                inTodoListRecycler
        )

    override fun onBindViewHolder(holder: TodoListRecyclerHolder, position: Int) {
       holder.bindWithView(
           this.titleList[position],
           this.stateList[position]
       )
    }

    override fun getItemCount(): Int = titleList.size

    fun submitList(titleList : ArrayList<String>, stateList : ArrayList<String>){
        this.titleList = titleList
        this.stateList = stateList
    }
}