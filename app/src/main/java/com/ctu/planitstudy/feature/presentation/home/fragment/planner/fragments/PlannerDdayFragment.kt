package com.ctu.planitstudy.feature.presentation.home.fragment.planner.fragments

import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentPlannerDDayBinding
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.ctu.planitstudy.feature.presentation.home.fragment.home.HomeViewModel
import com.ctu.planitstudy.feature.presentation.home.fragment.planner.recycler.DdayListRecyclerAdapter
import com.ctu.planitstudy.feature.presentation.home.fragment.planner.recycler.InDdayListRecycler
import io.reactivex.rxkotlin.toObservable

class PlannerDdayFragment : BaseFragment<FragmentPlannerDDayBinding>(), InDdayListRecycler {

    val TAG = "DdayFragment - 로그"
    
    override val bindingInflater: (LayoutInflater) -> FragmentPlannerDDayBinding
        get() = FragmentPlannerDDayBinding::inflate

    private val homeViewModel by activityViewModels<HomeViewModel>()

    private lateinit var dDayListRecyclerAdapter: DdayListRecyclerAdapter

    override fun setInit() {
        super.setInit()

        dDayListRecyclerAdapter = DdayListRecyclerAdapter(this)
        binding.plannerDDayRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapter = dDayListRecyclerAdapter
        }
        homeViewModel.homeState.value!!.dDayList!!.ddays.toObservable()
            .filter { it.isRepresentative }
            .subscribe {
                binding.apply {
                    plannerDDayRepresentativeDDay.text = "D-" + it.dDay
                    plannerDDayRepresentativeTitle.text = it.title
                    plannerDDayRepresentativeDate.text = it.endAt
                }
            }.isDisposed
        homeViewModel.homeState.value!!.dDayList!!.ddays.toObservable()
            .filter { !it.isRepresentative }
            .subscribe {
                dDayListRecyclerAdapter.dDayList.add(it)
                dDayListRecyclerAdapter.notifyDataSetChanged()
            }.isDisposed
    }

    override fun onClickedItem(position: Int) {
    }
}