package com.ctu.cashstudy.core.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

abstract class BaseBindingActivity<VB : ViewBinding>() : BaseActivity() {
    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> VB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)

        setup()
    }

    // 최초 액티비티 설정 시 사
    abstract fun setup()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}