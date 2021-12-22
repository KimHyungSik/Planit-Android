package com.ctu.planitstudy.core.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.ctu.planitstudy.feature.presentation.dialogs.LoadingDialog

abstract class BaseBindingActivity<VB : ViewBinding, VM : BaseViewModel>() : BaseActivity() {

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> VB
    abstract val viewModel: VM

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    lateinit var loading: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        loading = LoadingDialog(this)
        viewModel.loading.observe(
            this,
            {
                if (it != null)
                    when (it) {
                        is LoadingState.Show -> showLoading()
                        is LoadingState.Dismiss -> dismiss()
                        is LoadingState.ErrorDismiss -> dismiss()
                    }
            }
        )
        setup()
    }

    // 최초 액티비티 설정 시 사
    abstract fun setup()

    open fun showLoading() {
        loading.show()
    }

    open fun dismiss() {
        loading.dismiss()
    }
    open fun backScreen() {
        finish()
    }

    override fun onDestroy() {
        viewModel.loadingDismiss()
        _binding = null
        super.onDestroy()
    }
}
