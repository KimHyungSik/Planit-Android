package com.ctu.planitstudy.feature.presentation.home.fragment.planner.fragments

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.core.util.date_util.DateConvter
import com.ctu.planitstudy.core.util.enums.DdayIconSet
import com.ctu.planitstudy.databinding.FragmentPlannerDDayBinding
import com.ctu.planitstudy.feature.presentation.dday.DdayScreen
import com.ctu.planitstudy.feature.presentation.home.fragment.home.HomeViewModel
import com.ctu.planitstudy.feature.presentation.home.fragment.planner.recycler.DdayListRecyclerAdapter
import com.ctu.planitstudy.feature.presentation.home.fragment.planner.recycler.InDdayListRecycler
import io.reactivex.rxkotlin.toObservable
import java.time.LocalDate

class PlannerDdayFragment : BaseFragment<FragmentPlannerDDayBinding, HomeViewModel>(), InDdayListRecycler {

    val TAG = "DdayFragment - 로그"

    override val bindingInflater: (LayoutInflater) -> FragmentPlannerDDayBinding
        get() = FragmentPlannerDDayBinding::inflate

    override val viewModel by activityViewModels<HomeViewModel>()

    private lateinit var dDayListRecyclerAdapter: DdayListRecyclerAdapter

    override fun setInit() {
        super.setInit()

        dDayListRecyclerAdapter = DdayListRecyclerAdapter(this)
        binding.plannerDDayRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapter = dDayListRecyclerAdapter
        }
        // 대표 디데이
        viewModel.homeState.value!!.dDayList!!.ddays.toObservable()
            .filter { it.isRepresentative }
            .subscribe {
                dDay ->
                with(binding) {
                    plannerDDayRepresentativeDDay.text = if (dDay.dDay >= 0) "D-${dDay.dDay}" else if (LocalDate.now().toString() == dDay.createdAt) "D+0" else "D+${Math.abs(dDay.dDay)}"
                    plannerDDayRepresentativeTitle.text = dDay.title
                    plannerDDayRepresentativeDate.text = DateConvter.dtoDateToPointDate(dDay.endAt)
                    plannerDDayRepresentativeIcon.setImageResource(DdayIconSet.DdayIconImg.values()[DdayIconSet().dDayIconList.indexOf(dDay.icon)].imge)
                    plannerDDayRepresentativeItemView.setOnClickListener {
                        val intent = Intent(activity, DdayScreen::class.java)
                        intent.putExtra("dDay", dDay)
                        moveIntent(intent)
                    }
                    plannerDDayRepresentativeItemView.visibility = View.VISIBLE
                    notEmptyDdayList()
                }
            }.isDisposed

        // 대표 디데이를 제외한 디데이 리스
        viewModel.homeState.value!!.dDayList!!.ddays.toObservable()
            .filter { !it.isRepresentative }
            .subscribe {
                dDayListRecyclerAdapter.dDayList.add(it)
                dDayListRecyclerAdapter.notifyDataSetChanged()
                binding.plannerDDayRecyclerView.visibility = View.VISIBLE
                notEmptyDdayList()
            }.isDisposed
    }

    private fun notEmptyDdayList() {
        binding.apply {
            plannerDDayNestedScrollview.visibility = View.VISIBLE
            plannerDDayEmpty.visibility = View.GONE
        }
    }

    override fun onClickedItem(position: Int) {
        val intent = Intent(activity, DdayScreen::class.java)
        intent.putExtra("dDay", dDayListRecyclerAdapter.dDayList[position])
        moveIntent(intent)
    }
}
