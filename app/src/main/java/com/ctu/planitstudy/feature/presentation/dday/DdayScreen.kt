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
import com.ctu.planitstudy.feature.presentation.util.Screens
import com.jakewharton.rxbinding2.widget.RxRadioGroup
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

    private val ddayIconSet = DdayIconSet()

    override fun setup() {

        val dDay = intent.getParcelableExtra<DdayDto>("dDay")

        // 기존 데이터 여부 확인
        if (dDay == null) {
            binding.dDayDeletBtn.visibility = View.INVISIBLE
            setUpView()
        } else {
            setUpViewWithDday(dDay)
            viewModel.dDayDtoSet(dDay)
        }

        viewModel.apply {
            dDayUpdate(
                dDay ?: DdayDto(-1, "", false, "", "", "", -1),
                getDdayDateText(dDay?.endAt)
            )
        }

        binding.apply {
            viewmodel = viewModel
            dDayDatePicker.apply {
                setOnDateChangeListener { view, year, month, dayOfMonth ->
                    viewmodel!!.dDayUpdate(
                        viewmodel!!.dDayState.value!!.copy(
                            date = getDdayDateText("$year-${month + 1}-$dayOfMonth")
                        )
                    )
                }
            }
            dDayConfirmedDateBtn.setOnClickListener {
                dDayCustomDatePicker.visibility = View.INVISIBLE
                dDayBlur.visibility = View.INVISIBLE
                binding.invalidateAll()
            }
        }

        binding.invalidateAll()

        disposables.add(RxTextView.textChanges(binding.dDayEditTitle)
            .subscribe {
                binding.dDayTitleLengthCount.text = it.length.toString() + "/10"
            })

        binding.apply {
            dDayDateItemView.setOnClickListener {
                dDayCustomDatePicker.visibility = View.VISIBLE
                dDayBlur.visibility = View.VISIBLE
            }
            disposables.add(RxRadioGroup.checkedChanges(binding.dDayCustomIcon)
                .subscribe {
                    viewmodel!!.dDayUpdate(
                        viewmodel!!.dDayState.value!!.copy(
                            color = ddayIconSet.dDayIconList[ddayIconSet.dDayIconListId.indexOf(
                                it
                            )]
                        )
                    )
                }
            )
        }

        viewModelSet()
    }

    fun getDdayDateText(dDay: String?): String {
        return if (dDay != null) {
            calendar.time = dateFormatDdayDto.parse(dDay)
            dateFormatText.format(dateFormatDdayDto.parse(dDay)) + "(${
                Week.values()[calendar.get(Calendar.DAY_OF_WEEK)].week
            })"
        } else {
            calendar.time =
                dateFormatDdayDto.parse(dateFormatDdayDto.format(System.currentTimeMillis()))
            dateFormatText.format(System.currentTimeMillis()) + "(${
                Week.values()[calendar.get(Calendar.DAY_OF_WEEK)].week
            })"
        }
    }

    @SuppressLint("SetTextI18n")
    fun setUpViewWithDday(dDay: DdayDto) {
        binding.apply {
            dDayTitle.text = "디데이 편집하기"
            dDayCustomIcon.check(
                DdayIconSet.DdayIcon.values()[DdayIconSet().dDayIconList.indexOf(
                    dDay.color
                )].radio
            )
        }
    }

    fun setUpView() {
        binding.apply {
            dDayCustomIcon.check(DdayIconSet.DdayIcon.PINK.radio)
        }
    }

    fun viewModelSet(){
        viewModel.dDayNetworkState.observe(this, {
            Log.d(TAG, "viewModelSet: $it")
            if (it.deleteDay) moveIntentAllClear(Screens.HomeScreenSh().activity)
        })
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}