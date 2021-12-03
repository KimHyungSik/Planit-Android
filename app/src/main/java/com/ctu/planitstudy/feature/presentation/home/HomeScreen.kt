package com.ctu.planitstudy.feature.presentation.home

import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityHomeScreenBinding
import com.ctu.planitstudy.feature.presentation.home.fragment.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreen :
    BaseBindingActivity<ActivityHomeScreenBinding, HomeScreenViewModel>() {
    override val bindingInflater: (LayoutInflater) -> ActivityHomeScreenBinding
        get() = ActivityHomeScreenBinding::inflate

    override fun setup() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.home_fragment_view) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.homeBottomNav, navController)
    }

    override val viewModel: HomeScreenViewModel by viewModels()
}
