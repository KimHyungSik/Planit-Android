package com.ctu.planitstudy.core.util.binding_adapter

import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ctu.planitstudy.core.util.dp
import com.tbuonomo.viewpagerdotsindicator.setPaddingHorizontal

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("visibleOrGone")
    fun setVisibleOrGone(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("textSize")
    fun setTextSize(textView: TextView, size: Float) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    @JvmStatic
    @BindingAdapter("setHorizontalPadding")
    fun setHorizontalPadding(view: View, padding: Int) {
        view.setPaddingHorizontal(padding.dp)
    }
}
