package com.ctu.planitstudy.feature.presentation.common.action_bar

import androidx.appcompat.app.AppCompatActivity
import com.ctu.planitstudy.R
import com.google.android.material.appbar.MaterialToolbar

class ToolBarHelper {
    companion object {
        fun hideToolbar(activity: AppCompatActivity) {
            activity.supportActionBar?.let {
                it.hide()
            }
        }

        fun showToolbar(activity: AppCompatActivity, title: String? = null, titleResId: Int? = null) {
            activity.supportActionBar?.let { actionBar ->
                actionBar.show()
                title?.let { actionBar.setTitle(title) }
                titleResId?.let { actionBar.setTitle(titleResId) }
                actionBar.setHomeAsUpIndicator(null)
                actionBar.setDisplayHomeAsUpEnabled(false)
            }
        }

        fun showToolbarWithBackButton(activity: AppCompatActivity, title: String? = null, titleResId: Int? = null, buttonListener: (() -> Unit)? = null, toolBarLayout: Int? = null) {
            activity.supportActionBar?.let { actionBar ->
                actionBar.show()
                title?.let { actionBar.setTitle(title) }
                titleResId?.let { actionBar.setTitle(titleResId) }
                actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back)
                actionBar.setDisplayHomeAsUpEnabled(true)
                buttonListener?.let { listener ->
                    activity.findViewById<MaterialToolbar>(toolBarLayout ?: R.id.toolbar_layout)
                        .setNavigationOnClickListener {
                            listener()
                        }
                }
            }
        }
    }
}
