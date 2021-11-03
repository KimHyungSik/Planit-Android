package com.ctu.planitstudy

import android.view.LayoutInflater
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityMainBinding
import com.ctu.planitstudy.databinding.ActivitySignUpScreenBinding

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
            = ActivityMainBinding::inflate

    override fun setup() {
    }
}