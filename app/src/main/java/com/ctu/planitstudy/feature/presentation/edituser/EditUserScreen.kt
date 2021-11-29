package com.ctu.planitstudy.feature.presentation.edituser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityEditUserScreenBinding

class EditUserScreen : BaseBindingActivity<ActivityEditUserScreenBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityEditUserScreenBinding
        get() = ActivityEditUserScreenBinding::inflate

    override fun setup() {
    }
}