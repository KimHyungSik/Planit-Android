package com.ctu.planitstudy.feature.presentation.dday

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityDdayScreenBinding
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.ctu.planitstudy.feature.presentation.sign_up.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DdayScreen
    :BaseBindingActivity<ActivityDdayScreenBinding>()
{

    val TAG = "DdayScreen - 로그"

    override val bindingInflater: (LayoutInflater) -> ActivityDdayScreenBinding
        get() = ActivityDdayScreenBinding::inflate

    private val viewModel : DdayViewModel by viewModels()

    override fun setup() {

        val dDay = intent.getParcelableExtra<DdayDto>("dDay")

        if(dDay == null){
            binding.dDayDeletBtn.visibility = View.INVISIBLE
        }else{
            Log.d(TAG, "setup: $dDay")
        }

        binding.dDayDateCalendarIcon.setOnClickListener {
        }

    }
}