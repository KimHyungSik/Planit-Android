package com.ctu.planitstudy.feature.presentation.dday

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.core.util.enum.DdayIconSet
import com.ctu.planitstudy.core.util.enum.Week
import com.ctu.planitstudy.databinding.ActivityDdayScreenBinding
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log

@AndroidEntryPoint
@SuppressLint("SimpleDateFormat")
class DdayScreen
    : BaseBindingActivity<ActivityDdayScreenBinding>() {

    val TAG = "DdayScreen - 로그"

    override val bindingInflater: (LayoutInflater) -> ActivityDdayScreenBinding
        get() = ActivityDdayScreenBinding::inflate

    private val viewModel: DdayViewModel by viewModels()
    private val calendar = Calendar.getInstance()
    private val dateFormatDdayDto = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    private val dateFormatText = SimpleDateFormat("yyyy년MM월dd일", Locale.KOREA)
    private val disposables = CompositeDisposable()

    override fun setup() {

        val dDay = intent.getParcelableExtra<DdayDto>("dDay")

        if (dDay == null) {
            binding.dDayDeletBtn.visibility = View.INVISIBLE
            setUpView()
        } else {
            setUpViewWithDday(dDay)
        }

        binding.apply {
            viewmodel = viewModel
        }

//        viewModel.apply {
//            dDayUpdate(dDay ?: DdayDto(-1,"",false,"","","",-1))
//        }

        disposables.add(RxTextView.textChanges(binding.dDayEditTitle)
            .subscribe {
                binding.dDayTitleLengthCount.text = it.length.toString() + "/10"
            })

    }

    @SuppressLint("SetTextI18n")
    fun setUpViewWithDday(dDay: DdayDto) {
        calendar.time = dateFormatDdayDto.parse(dDay.endAt)
        binding.apply {
            dDayTitle.text = "디데이 편집하기"
            dDayDateText.text =
                dateFormatText.format(dateFormatDdayDto.parse(dDay.endAt)) + "(${
                    Week.values()[calendar.get(Calendar.DAY_OF_WEEK)].week
                })"
            dDayEditTitle.setText(dDay.title)
            dDayTitleLengthCount.text = dDay.title.length.toString() + "/10"
            dDayRepresentativeSwitch.isChecked = dDay.isRepresentative
            dDayCustomIcon.check(
                DdayIconSet.DdayIcon.values()[DdayIconSet().dDayIconList.indexOf(
                    dDay.color
                )].radio
            )
        }
    }

    fun setUpView() {
        calendar.time =
            dateFormatDdayDto.parse(dateFormatDdayDto.format(System.currentTimeMillis()))
        binding.apply {
            dDayDateText.text =
                dateFormatText.format(System.currentTimeMillis()) + "(${
                    Week.values()[calendar.get(Calendar.DAY_OF_WEEK)].week
                })"
            dDayCustomIcon.check(DdayIconSet.DdayIcon.PINK.radio)
        }
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}