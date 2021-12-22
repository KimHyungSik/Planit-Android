package com.ctu.planitstudy.feature.presentation.timer

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityTimerScreenBinding
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyDto
import com.ctu.planitstudy.feature.presentation.timer.dialog.TimerBreakTimeDialog
import com.ctu.planitstudy.feature.presentation.timer.dialog.TimerStartDialog
import com.ctu.planitstudy.feature.presentation.timer.dialog.TimerStopDialog
import com.ctu.planitstudy.feature.presentation.util.Screens
import com.google.android.gms.ads.AdRequest

class TimerScreen : BaseBindingActivity<ActivityTimerScreenBinding, TimerViewModel>() {

    val TAG = "TimerScreen - 로그"

    override val bindingInflater: (LayoutInflater) -> ActivityTimerScreenBinding
        get() = ActivityTimerScreenBinding::inflate

    override val viewModel: TimerViewModel by viewModels()

    var study: StudyDto? = null
    private val timerStartDialog = TimerStartDialog()
    private val timerStopDialog = TimerStopDialog()
    private val timerBreakTimeDialog = TimerBreakTimeDialog()

    @SuppressLint("ClickableViewAccessibility")
    override fun setup() {

        study = intent.getParcelableExtra("studyDto")

        viewModel.setStudyDto(study!!)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        // 화면 자동 꺼짐 방지
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        timerStartDialog.show(
            supportFragmentManager, "TimerStartDialog"
        )

        val intentFilter = IntentFilter(Intent.ACTION_SCREEN_OFF)
        intentFilter.addAction(Intent.ACTION_SCREEN_ON)

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val action = intent!!.action

                when (action) {
                    Intent.ACTION_SCREEN_ON -> {
                        viewModel.changeTimerCycle(TimerCycle.TimeFlow)
                    }
                    Intent.ACTION_SCREEN_OFF -> {
                        viewModel.changeTimerCycle(TimerCycle.TimeReady)
                    }
                }
            }
        }
        registerReceiver(receiver, intentFilter)

        with(binding) {
            with(timerCircularBar) {
                setOnTouchListener { _, _ -> true }
                max = 3600f
            }
            viewmodel = viewModel
            activity = this@TimerScreen
        }

        viewModel.timerState.observe(
            this,
            {
                binding.timerCircularBar.progress = it.time.toFloat()
                binding.invalidateAll()
            }
        )

        viewModel.timerCycle.observe(
            this,
            {
                when (it) {
                    TimerCycle.TimeFlow -> {
                        timerStartAndStop(true)
                        timerBtn(true)
                    }
                    TimerCycle.TimeStop -> {
                        timerStartAndStop(false)
                        val intent = Intent(this, Screens.MeasurementScreenSh.activity)
                        intent.putExtra("studyDto", study)
                        intent.putExtra("time", viewModel.timerState.value!!.time)
                        intent.putExtra("totalStar", viewModel.timerState.value!!.star)
                        intent.putExtra("totalTicket", viewModel.timerState.value!!.ticket)
                        intent.putExtra("brakeTime", viewModel.timerState.value!!.breakTime)
                        moveIntent(intent)
                        timerBtn(false)
                    }
                    TimerCycle.TimePause -> {
                        timerStartAndStop(false)
                        showStopDialog()
                        timerBtn(false)
                    }
                    TimerCycle.TimerExited -> {
                        finish()
                    }
                    TimerCycle.TimeBreak -> {
                        timerStartAndStop(false)
                        timerBreakTimeDialog.show(
                            supportFragmentManager, "TimerBreakTimeDialog"
                        )
                        timerBtn(false)
                    }
                    TimerCycle.TimeReady -> {
                        timerStartAndStop(false)
                        timerBtn(false)
                    }
                }
            }
        )
    }

    private fun timerBtn(start: Boolean) {
        if (start) {
            with(binding) {
                timerStartBtn.visibility = View.GONE
                timerStopBtn.visibility = View.VISIBLE
            }
        } else {
            with(binding) {
                timerStartBtn.visibility = View.VISIBLE
                timerStopBtn.visibility = View.GONE
            }
        }
    }

    private fun showStopDialog() {
        timerStopDialog.show(
            supportFragmentManager, "TimerStopDialog"
        )
    }

    private fun timerStartAndStop(b: Boolean) {
        if (b && !viewModel.timerThreadState)
            viewModel.startTimer()
        if (!b && viewModel.timerThreadState)
            viewModel.stopTimer()
    }

    override fun onStop() {
        viewModel.changeTimerCycle(TimerCycle.TimeReady)
        super.onStop()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showStopDialog()
            return false
        }

        return super.onKeyDown(keyCode, event)
    }
}
