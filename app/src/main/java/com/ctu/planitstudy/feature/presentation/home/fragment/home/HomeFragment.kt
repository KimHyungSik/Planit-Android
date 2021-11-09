package com.ctu.planitstudy.feature.presentation.home.fragment.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentAnalysisBinding
import com.ctu.planitstudy.databinding.FragmentHomeBinding
import com.ctu.planitstudy.feature.presentation.home.fragment.home.recycler.InTodoListRecycler
import com.ctu.planitstudy.feature.presentation.home.fragment.home.recycler.TodoListRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), InTodoListRecycler {

    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private lateinit var todoListRecyclerAdapter: TodoListRecyclerAdapter

    private val viewModel by viewModels<HomeViewModel>()

    override fun setUpViews() {
        super.setUpViews()

        todoListRecyclerAdapter = TodoListRecyclerAdapter(this)

        binding.homeTodoRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapter = todoListRecyclerAdapter
        }

        binding.viewmodel = viewModel

        todoListRecyclerAdapter.submitList(arrayListOf("test","test","test","test"),arrayListOf("test","test","test","test"))
    }

    override fun onClickedItem(position: Int) {
    }
}