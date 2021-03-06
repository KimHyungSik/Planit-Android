package com.ctu.planitstudy.feature.presentation.dday

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.core.util.date_util.DateConvter
import com.ctu.planitstudy.core.util.enums.DdayIconSet
import com.ctu.planitstudy.databinding.ActivityDdayScreenBinding
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.ctu.planitstudy.feature.presentation.dday.dialog.DeleteCheckDialog
import com.ctu.planitstudy.feature.presentation.dday.dialog.RepresentativeCheckDialog
import com.ctu.planitstudy.feature.presentation.dialogs.BottomSheetCalendarDialog
import com.ctu.planitstudy.feature.presentation.dialogs.SingleTitleCheckDialog
import com.ctu.planitstudy.feature.presentation.util.Screens
import com.jakewharton.rxbinding2.widget.RxRadioGroup
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
@SuppressLint("SimpleDateFormat")
class DdayScreen :
    BaseBindingActivity<ActivityDdayScreenBinding, DdayViewModel>(),
    BottomSheetCalendarDialog.BottomSheetCalendar {

    val TAG = "DdayScreen - 로그"

    override val bindingInflater: (LayoutInflater) -> ActivityDdayScreenBinding
        get() = ActivityDdayScreenBinding::inflate

    override val viewModel: DdayViewModel by viewModels()
    private val disposables = CompositeDisposable()

    private val ddayIconSet = DdayIconSet()
    private var representativeSwitchOnesCheck = false
    private var exitRepresentativeSwitchOnesCheck = false
    private val calendarDialog = BottomSheetCalendarDialog()
    private val deleteDialog = DeleteCheckDialog()

    override fun setup() {
        val dDay = intent.getParcelableExtra<DdayDto>("dDay")

        // 기존 데이터 여부 확인
        if (dDay == null) {
            binding.dDayDeletBtn.visibility = View.INVISIBLE
            setUpView()
        } else {
            exitRepresentativeSwitchOnesCheck = true
            setUpViewWithDday(dDay)
        }

        with(viewModel) {
            dDayUpdate(
                dDay ?: DdayDto(-1, "", false, "", "", "", -1),
                DateConvter.dtoDateToTextDate(dDay?.endAt)
            )
        }

        with(binding) {
            viewmodel = viewModel

            dDayDateItemView.setOnClickListener {
                showCalendar(DateConvter.textDateToLongDate(viewModel.dDayState.value!!.date))
            }

            disposables.add(
                RxRadioGroup.checkedChanges(binding.dDayCustomIcon)
                    .subscribe {
                        viewmodel!!.dDayUpdate(
                            viewmodel!!.dDayState.value!!.copy(
                                icon = ddayIconSet.dDayIconList[
                                    ddayIconSet.dDayIconListId.indexOf(
                                        it
                                    )
                                ]
                            )
                        )
                    }
            )
            dDayBackScreenBtn.setOnClickListener {
                finish()
            }
        }

        disposables.add(
            RxTextView.textChanges(binding.dDayEditTitle)
                .subscribe {
                    binding.dDayTitleLengthCount.text = it.length.toString() + "/10"
                }
        )

        binding.invalidateAll()
        viewModelSet()
    }

    // 기존에 데이터가 있을 경우
    @SuppressLint("SetTextI18n")
    fun setUpViewWithDday(dDay: DdayDto) {
        binding.apply {
            dDayTitle.text = "디데이 편집하기"
            dDayCustomIcon.check(
                DdayIconSet.DdayIcon.values()[
                    DdayIconSet().dDayIconList.indexOf(
                        dDay.icon
                    )
                ].radio
            )
            dDayConfirmedBtnText.text = "저장하기"
        }
        viewModel.dDayDtoSet(dDay)
    }

    // 기존데이터가 없는 경우
    private fun setUpView() {
        binding.dDayCustomIcon.check(DdayIconSet.DdayIcon.FULLMOON.radio)
    }

    private fun viewModelSet() {
        viewModel.dDayNetworkState.observe(
            this,
            {
                Log.d(TAG, "viewModelSet: dDayNetworkState $it")
                if (it.deleteDay || it.modifiedDay || it.addDday) moveIntentAllClear(Screens.HomeScreenSh.activity)
            }
        )

        // 디데이 데이터 관리
        viewModel.dDayState.observe(
            this,
            {
                Log.d(TAG, "viewModelSet: dDayState $it")
                if (it.representative && !representativeSwitchOnesCheck) {
                    if (exitRepresentativeSwitchOnesCheck)
                        exitRepresentativeSwitchOnesCheck = false
                    else {
                        RepresentativeCheckDialog().show(
                            supportFragmentManager, "RepresentativeCheckDialog"
                        )
                        representativeSwitchOnesCheck = true
                    }
                }
                binding.dDayRepresentativeSwitch.isChecked = it.representative
            }
        )

        // 팝업 상태 관리
        viewModel.dDayDialogState.observe(
            this,
            {
                val arg = Bundle()
                Log.d(TAG, "viewModelSet: dDayDialogState $it")
                if (it.deleteDialog)
                    deleteDialog.show(
                        supportFragmentManager, "DeleteCheckDialog"
                    )
                if (it.emptyTitleDialog) {
                    arg.putString("title", getString(R.string.empty_dialog_fragment))
                    showDialogFragment(arg, SingleTitleCheckDialog())
                }
            }
        )
    }

    private fun showCalendar(time: Long) {
        val args = Bundle()
        args.putLong("date", time)
        calendarDialog.arguments = args
        calendarDialog.setDialogListener(this)
        calendarDialog.show(supportFragmentManager, "calendarDialog")
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    override fun onConfirmedClick() {
        binding.invalidateAll()
    }

    override fun onChangeDate(year: Int, month: Int, dayOfMonth: Int) {
        viewModel!!.dDayUpdate(
            viewModel!!.dDayState.value!!.copy(
                date = DateConvter.dtoDateToTextDate("$year-${month + 1}-$dayOfMonth")
            )
        )
    }
}
