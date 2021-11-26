package com.ctu.planitstudy.feature.presentation.timer

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.activity.viewModels
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityTimerScreenBinding
import com.ctu.planitstudy.feature.presentation.timer.dialog.TimerStartDialog
import com.ctu.planitstudy.feature.presentation.timer.dialog.TimerStopDialog
import com.ctu.planitstudy.feature.presentation.util.ActivityLifeCycleObserver
import com.ctu.planitstudy.feature.presentation.util.Screens

class TimerScreen : BaseBindingActivity<ActivityTimerScreenBinding>() {

    val TAG = "TimerScreen - 로그"

    override val bindingInflater: (LayoutInflater) -> ActivityTimerScreenBinding
        get() = ActivityTimerScreenBinding::inflate

    private val viewModel: TimerViewModel by viewModels()

    private var observer = ActivityLifeCycleObserver(lifecycle)

    @SuppressLint("ClickableViewAccessibility")
    override fun setup() {

        // 화면 자동 꺼짐 방지
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        TimerStartDialog().show(
            supportFragmentManager, "TimerStartDialog"
        )

        val intentFilter = IntentFilter(Intent.ACTION_SCREEN_OFF)
        intentFilter.addAction(Intent.ACTION_SCREEN_ON)
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val action = intent!!.action
                Log.d("로그", "receive : $action")

                when (action) {
                    Intent.ACTION_SCREEN_ON -> {
                        // do something
                    }
                    Intent.ACTION_SCREEN_OFF -> {
                        // do something
                    }
                }
            }
        }
        lifecycle.addObserver(observer)
        registerReceiver(receiver, intentFilter)

        with(binding) {
            with(timerCircularBar) {
                setOnTouchListener { v, event -> true }
                max = 3600f
            }
            timerStopBtn.setOnClickListener {
                viewModel.changeTimerCycle(TimerCycle.TimePause)
            }
        }

        viewModel.timerState.observe(this, {
            binding.timerTimeText.text = it.timeString
            binding.timerCircularBar.progress = it.time.toFloat()
            when (it.timerCycle) {
                TimerCycle.TimeFlow -> {
                }
                TimerCycle.TimeStop -> {
                    moveIntent(Screens.MeasurementScreenSh.activity)
                }
                TimerCycle.TimePause -> {
                    TimerStopDialog().show(
                        supportFragmentManager, "TimerStopDialog"
                    )
                }
                TimerCycle.TimerExited -> {
                    finish()
                }
            }
        })
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            TimerStopDialog().show(
                supportFragmentManager, "TimerStopDialog"
            )
            return false
        }

        return super.onKeyDown(keyCode, event)
    }
}
