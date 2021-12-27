package com.ctu.planitstudy.core.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ctu.planitstudy.feature.presentation.dialogs.LoadingDialog

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {
    protected lateinit var binding: VB
    abstract val bindingInflater: (LayoutInflater) -> VB
    abstract val viewModel: VM

    lateinit var loading: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loading = LoadingDialog(requireContext())
        viewModel.loading.observe(
            viewLifecycleOwner,
            {
                if (it != null)
                    when (it) {
                        is LoadingState.Show -> showLoading()
                        is LoadingState.Dismiss -> dismiss()
                        is LoadingState.ErrorDismiss -> dismiss()
                    }
            }
        )
        setInit()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setOnStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpViews()
        observeData()
        super.onViewCreated(view, savedInstanceState)
    }

    open fun showLoading() {
        loading.show()
    }

    open fun dismiss() {
        loading.dismiss()
    }

    open fun setUpViews() {}

    open fun observeData() {}

    open fun setInit() {}

    open fun setOnStart() {}

    // fragment 최초 설정
    private fun init() {
        binding = bindingInflater.invoke(layoutInflater)
    }

    override fun onDestroyView() {
        viewModel.loadingDismiss()
        super.onDestroyView()
    }

    open fun moveIntent(activity: Class<*>) {
        val intent = Intent(getActivity(), activity)
        startActivity(intent)
    }

    open fun moveIntent(intent: Intent) {
        startActivity(intent)
    }

    open fun showDialogFragment(arg: Bundle, fragment: DialogFragment) {
        fragment.arguments = arg
        fragment.show(parentFragmentManager, "dialog")
    }

    open fun moveIntentAllClear(activity: Class<*>) {
        val intent = Intent(getActivity(), activity)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
            Intent.FLAG_ACTIVITY_CLEAR_TASK or
            Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    open fun moveIntentAllClear(intent: Intent) {
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
            Intent.FLAG_ACTIVITY_CLEAR_TASK or
            Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
