package com.ctu.planitstudy.feature.presentation.home.fragment.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.core.util.date_util.DateCalculation
import com.ctu.planitstudy.core.util.longToTimeKorString
import com.ctu.planitstudy.databinding.FragmentHomeBinding
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.recycler.study.InStudyListRecycler
import com.ctu.planitstudy.feature.presentation.recycler.study.StudyListMode
import com.ctu.planitstudy.feature.presentation.recycler.study.StudyListRecyclerAdapter
import com.ctu.planitstudy.feature.presentation.util.Screens
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxkotlin.toObservable

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), InStudyListRecycler {

    val TAG = "HomFragment - 로그"

    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private lateinit var studyListRecyclerAdapter: StudyListRecyclerAdapter

    override val viewModel by activityViewModels<HomeViewModel>()
    var totalTime = 0
    var totalString: String = ""
    var studyTimeTitle = "공부했어요"

    override fun setUpViews() {
        super.setUpViews()
        studyListRecyclerAdapter = StudyListRecyclerAdapter(this)
        binding.homeTodoRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapter = studyListRecyclerAdapter
        }
    }

    override fun setInit() {
        super.setInit()
        binding.viewmodel = viewModel
        binding.activity = this
        viewModel.changeStudyDate(DateCalculation().getCurrentDateString(0))
        viewModel.homeState.observe(
            this,
            Observer {
                totalTime = 0
                for (n in it.studyListDto.studies)
                    totalTime += n.recordedTime

                if (totalTime == 0) {
                    studyTimeTitle = "공부를 응원합니다!"
                } else {
                    studyTimeTitle = "공부했어요"
                    totalString = totalTime.toLong().longToTimeKorString()
                }

                binding.invalidateAll()

                if (it.dDayList != null) {
                    it.dDayList.ddays.toObservable()
                        .filter { it.isRepresentative }
                        .subscribe { dDay ->
                            binding.apply {
                                homeFragmentDDayCount.text =
                                    if (dDay.dDay > 0) "D-${dDay.dDay}" else if (dDay.dDay == 0) "D-DAY" else "D+${
                                    Math.abs(dDay.dDay)
                                    }"
                                if (dDay.dDay == 0) {
                                    homeFragmentDDayCountBox.background = ContextCompat.getDrawable(
                                        CashStudyApp.instance,
                                        R.drawable.d_day_gradation
                                    )
                                    homeFragmentDDayCount.setShadowLayer(4f, 0f, 2f, R.color.text_shadows_color)
                                }
                                homeFragmentDDayTitle.text = dDay.title
                                homeFragmentDDayColumn.visibility = View.VISIBLE
                                homeFragmentDDayColumn.setOnClickListener {
                                    val intent = Intent(context, Screens.DdayScreenSh.activity)
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

                studyListRecyclerAdapter.submitList(
                    it.studyListDto,
                    StudyListMode.HomeStudyListMode
                )
                studyListRecyclerAdapter.notifyDataSetChanged()
            }
        )

        binding.homeFragmentEmptyRepresentative.setOnClickListener {
            moveIntent(Screens.DdayScreenSh.activity)
        }
    }

    fun addStudyScreen() {
        moveIntent(Screens.StudyScreenSh.activity)
    }

    override fun onClickedItem(position: Int) {
        val intent = Intent(activity, Screens.TimerScreenSh.activity)
        intent.putExtra("studyDto", studyListRecyclerAdapter.studyList.studies[position])
        moveIntent(intent)
    }

    override fun onClickedCheckbox(position: Int, check: Boolean) {
    }
}
