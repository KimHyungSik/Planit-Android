package com.ctu.planitstudy.feature.presentation.home.fragment.home

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.core.util.date_util.DateCalculation
import com.ctu.planitstudy.databinding.FragmentHomeBinding
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.recycler.study.InStudyListRecycler
import com.ctu.planitstudy.feature.presentation.recycler.study.StudyListMode
import com.ctu.planitstudy.feature.presentation.recycler.study.StudyListRecyclerAdapter
import com.ctu.planitstudy.feature.presentation.util.Screens
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxkotlin.toObservable

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), InStudyListRecycler {

    val TAG = "HomFragmentR - 로그"

    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private lateinit var studyListRecyclerAdapter: StudyListRecyclerAdapter

    private val viewModel by activityViewModels<HomeViewModel>()

    override fun setUpViews() {
        super.setUpViews()

        studyListRecyclerAdapter = StudyListRecyclerAdapter(this)

        binding.homeTodoRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapter = studyListRecyclerAdapter
        }
        binding.homeFragmentAddStudy.setOnClickListener {
            moveIntent(Screens.StudyScreenSh.activity)
        }
    }

    override fun setInit() {
        super.setInit()
        binding.viewmodel = viewModel
        viewModel.homeState.observe(
            this,
            Observer {
                if (it.dDayList != null) {
                    it.dDayList.ddays.toObservable()
                        .filter { it.isRepresentative }
                        .subscribe {
                            dDay ->
                            binding.apply {
                                homeFragmentDDayCount.text = if (dDay.dDay > 0) "D-${dDay.dDay}" else if (dDay.dDay == 0)"D-DAY" else "D+${Math.abs(dDay.dDay)}"
                                if (dDay.dDay == 0)
                                    homeFragmentDDayCountBox.background = ContextCompat.getDrawable(CashStudyApp.instance, R.drawable.d_day_gradation)
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
                with(binding) {
                    if (it.studyListDto.studies.isEmpty()) {
                        homeFragmentStudyEmptyImg.visibility = View.VISIBLE
                        homeTodoRecyclerView.visibility = View.GONE
                    } else {
                        homeFragmentStudyEmptyImg.visibility = View.GONE
                        homeTodoRecyclerView.visibility = View.VISIBLE
                    }
                }
                studyListRecyclerAdapter.submitList(it.studyListDto, StudyListMode.HomeStudyListMode)
                studyListRecyclerAdapter.notifyDataSetChanged()
            }
        )

        binding.homeFragmentEmptyRepresentative.setOnClickListener {
            moveIntent(Screens.DdayScreenSh.activity)
        }
    }

    override fun setOnStart() {
        super.setOnStart()
        viewModel.changeStudyDate(DateCalculation().getCurrentDateString(0))
    }

    override fun onClickedItem(position: Int) {
        val intent = Intent(activity, Screens.StudyScreenSh.activity)
        intent.putExtra("studyDto", studyListRecyclerAdapter.studyList.studies[position])
        moveIntent(Screens.TimerScreenSh.activity)
    }
}
