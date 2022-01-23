package com.ctu.planitstudy.feature.presentation.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityHomeScreenBinding
import com.ctu.planitstudy.feature.presentation.common.action_bar.ToolBarHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreen :
    BaseBindingActivity<ActivityHomeScreenBinding, HomeScreenViewModel>() {
    override val viewModel: HomeScreenViewModel by viewModels()
    override val bindingInflater: (LayoutInflater) -> ActivityHomeScreenBinding
        get() = ActivityHomeScreenBinding::inflate

    private val _bottomNavVisible = MutableLiveData<Boolean>()
    val bottomNavVisible: LiveData<Boolean> = _bottomNavVisible

    @SuppressLint("ResourceType")
    override fun setup() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.home_fragment_view) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.homeBottomNav, navController)

        setSupportActionBar(binding.actionbar.toolbarLayout)

        ToolBarHelper.hideToolbar(this)
        visibleBottomNav(true)

        binding.activity = this
        binding.lifecycleOwner = this
    }

    fun visibleBottomNav(visible: Boolean) {
        _bottomNavVisible.value = visible
    }
}
