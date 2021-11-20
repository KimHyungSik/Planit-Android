package com.ctu.planitstudy.feature.presentation.home.fragment.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.core.util.date_util.DateCalculation
import com.ctu.planitstudy.databinding.FragmentHomeBinding
import com.ctu.planitstudy.feature.presentation.dday.DdayScreen
import com.ctu.planitstudy.feature.presentation.home.fragment.home.recycler.InTodoListRecycler
import com.ctu.planitstudy.feature.presentation.home.fragment.home.recycler.TodoListRecyclerAdapter
import com.ctu.planitstudy.feature.presentation.util.Screens
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxkotlin.toObservable

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), InTodoListRecycler {

    val TAG = "HomFragmentR - 로그"

    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private lateinit var todoListRecyclerAdapter: TodoListRecyclerAdapter

    private val viewModel by activityViewModels<HomeViewModel>()

    override fun setUpViews() {
        super.setUpViews()

        todoListRecyclerAdapter = TodoListRecyclerAdapter(this)

        binding.homeTodoRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapter = todoListRecyclerAdapter
        }
        binding.homeFragmentAddStudy.setOnClickListener {
            moveIntent(Screens.StudyScreenSh.activity)
        }

    }

    override fun setInit() {
        super.setInit()
        binding.viewmodel = viewModel
        viewModel.homeState.observe(this, Observer {
            if (it.dDayList != null) {
                it.dDayList.ddays.toObservable()
                    .filter { it.isRepresentative }
                    .subscribe {
                        dDay ->
                        binding.apply {
                            homeFragmentDDayCount.text = "D - " + dDay.dDay
                            homeFragmentDDayTitle.text = dDay.title
                            homeFragmentDDayColumn.visibility = View.VISIBLE
                            homeFragmentDDayColumn.setOnClickListener {
                                val intent = Intent(activity, Screens.DdayScreenSh.activity)
                                intent.putExtra("dDay", dDay)
                                moveIntent(intent)
                            }
                        }
                    }
            }
            todoListRecyclerAdapter.submitList(it.studyListDto)
            todoListRecyclerAdapter.notifyDataSetChanged()
        })

        binding.homeFragmentEmptyRepresentative.setOnClickListener {
            moveIntent(Screens.DdayScreenSh.activity)
        }
    }

    override fun onClickedItem(position: Int) {
    }
}