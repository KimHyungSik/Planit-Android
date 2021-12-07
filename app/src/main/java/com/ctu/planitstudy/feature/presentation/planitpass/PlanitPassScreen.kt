package com.ctu.planitstudy.feature.presentation.planitpass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityPlanitPassScreenBinding

class PlanitPassScreen : BaseBindingActivity<ActivityPlanitPassScreenBinding, PlanitPassViewModel>() {
    override val bindingInflater: (LayoutInflater) -> ActivityPlanitPassScreenBinding
        get() = ActivityPlanitPassScreenBinding::inflate
    override val viewModel: PlanitPassViewModel by viewModels()

    override fun setup() {
    }


}