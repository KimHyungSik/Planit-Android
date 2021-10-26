package com.ctu.cashstudy.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB: ViewBinding> : Fragment() {

    protected lateinit var binding: VB
    protected abstract fun getViewBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setInit()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpViews()
        observeData()
        super.onViewCreated(view, savedInstanceState)
    }

    open fun setUpViews() {}

    open fun observeData() {}

    open fun setInit(){}

    // fragment 최초 설정
    private fun init() {
        binding = getViewBinding()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}