package com.ctu.planitstudy.feature.presentation.home.fragment.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.core.util.DateCalculation
import com.ctu.planitstudy.databinding.FragmentAnalysisBinding
import com.ctu.planitstudy.databinding.FragmentHomeBinding
import com.ctu.planitstudy.feature.presentation.home.fragment.home.recycler.InTodoListRecycler
import com.ctu.planitstudy.feature.presentation.home.fragment.home.recycler.TodoListRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import io.reactivex.rxkotlin.toObservable
import java.time.LocalDate

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), InTodoListRecycler {

    val TAG = "HomFragmentR - 로그"

    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private lateinit var todoListRecyclerAdapter: TodoListRecyclerAdapter

    private val viewModel by activityViewModels<HomeViewModel>()

    private val dateCalculation = DateCalculation()

    override fun setUpViews() {
        super.setUpViews()

        todoListRecyclerAdapter = TodoListRecyclerAdapter(this)

        binding.homeTodoRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapter = todoListRecyclerAdapter
        }

        todoListRecyclerAdapter.submitList(arrayListOf("test","test","test","test"),arrayListOf("test","test","test","test"))


    }

    override fun setInit() {
        super.setInit()
        binding.viewmodel = viewModel
        viewModel.homeState.observe(this, Observer {
            if (it.dDayList != null) {
                it.dDayList.ddays.toObservable()
                    .filter { it.isRepresentative }
                    .subscribe {
                        binding.apply {
                            homeFragmentDDayCount.text = "D -" + it.dDay
                            homeFragmentDDayTitle.text = it.title
                            homeFragmentDDayColumn.visibility = View.VISIBLE
                        }
                    }
            }
        })
    }

    override fun onClickedItem(position: Int) {
    }
}