package com.ctu.planitstudy.core.util.binding_adapter

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("visibleOrGone")
    fun setVisibleOrGone(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}
