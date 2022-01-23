package com.ctu.planitstudy.core.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.ctu.planitstudy.feature.presentation.dialogs.LoadingDialog
import kotlinx.coroutines.Job

abstract class BaseBindingActivity<VB : ViewBinding, VM : BaseViewModel>() : BaseActivity() {

    open val mainJob = Job()

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> VB
    abstract val viewModel: VM

    protected val binding: VB
        get() = _binding!! as VB

    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)

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
        loadingDialog.show()
    }

    open fun dismiss() {
        loadingDialog.dismiss()
    }
    open fun backScreen() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mainJob.cancel()
    }
}
