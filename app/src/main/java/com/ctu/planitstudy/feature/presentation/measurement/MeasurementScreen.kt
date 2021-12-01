package com.ctu.planitstudy.feature.presentation.measurement

import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityMeasurementTimerBinding
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyDto
import com.ctu.planitstudy.feature.presentation.util.Screens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeasurementScreen : BaseBindingActivity<ActivityMeasurementTimerBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMeasurementTimerBinding
        get() = ActivityMeasurementTimerBinding::inflate

    val viewModel: MeasurementViewModel by viewModels()

    override fun setup() {
        viewModelSetUp()
        binding.viewmodel = viewModel
    }

    private fun viewModelSetUp() {
        with(viewModel) {
            updateMeasurementState(
                viewModel.measurementState.value!!.copy(
                    studyDto = intent.getParcelableExtra<StudyDto>("studyDto"),
                    measurementTime = intent.getLongExtra("time", 0),
                    totalBrakeTime = intent.getIntExtra("brakeTime", 0),
                    totalStar = intent.getIntExtra("totalStar", 0),
                    totalTicket = intent.getIntExtra("totalTicket", 0),
                )
            )
            getExistingMeasurementTime()

            measurementState.observe(this@MeasurementScreen, {
                Log.d(TAG, "viewModelSetUp: $it")
                with(binding) {
                    invalidateAll()
                    // 체크 유무 확인
                    if (it.studyDto!!.isDone) {
                        measurementStudyConfirm.visibility = View.VISIBLE
                        measurementStudyCheckedView.visibility = View.GONE
                    } else {
                        measurementStudyConfirm.visibility = View.GONE
                        measurementStudyCheckedView.visibility = View.VISIBLE
                    }

                    // 저장 확인
                    if (it.onExit)
                        moveIntentAllClear(Screens.HomeScreenSh.activity)
                }
            })
        }
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            viewModel.recordMeasurementTimer(viewModel.measurementState.value!!.isDone)
            return false
        }

        return super.onKeyDown(keyCode, event)
    }
}
