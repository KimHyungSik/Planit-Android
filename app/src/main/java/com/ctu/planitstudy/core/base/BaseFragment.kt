package com.ctu.planitstudy.core.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ctu.planitstudy.R
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.common.popup.PopupData
import com.ctu.planitstudy.feature.presentation.common.popup.PopupHelper
import com.ctu.planitstudy.feature.presentation.dialogs.LoadingDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {

    private var _binding: ViewBinding? = null
    protected val binding: VB
        get() = _binding as VB
    abstract val bindingInflater: (LayoutInflater) -> VB
    abstract val viewModel: VM

    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(requireContext())
    }
    var loadingState: Boolean = false

    open val mainJob = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setOnStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
        setUpViews()
        observeData()
        super.onViewCreated(view, savedInstanceState)
    }

    open fun showLoading() {
        loadingState = true
        loadingDialog.show()
        CoroutineScope(Dispatchers.Default + mainJob).launch {
            delay(3000)
            if (loadingState) {
                dismiss()
            }
        }
    }

    open fun dismiss() {
        loadingDialog.dismiss()
        loadingState = false
    }

    open fun setUpViews() {}

    open fun observeData() {}

    open fun setInit() {}

    open fun setOnStart() {}

    // fragment 최초 설정
    private fun init() {
        _binding = bindingInflater.invoke(layoutInflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        dismiss()
    }

    override fun onDestroyView() {
        viewModel.loadingDismiss()
        mainJob.cancel()
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
        fragment.show(childFragmentManager, "dialog")
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

    open fun moveIntentAffinity(intent: Intent) {
        activity?.let { act ->
            act.finishAffinity()
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    open fun moveIntentAffinity(acti: Class<*>) {
        val intent = Intent(getActivity(), acti)
        activity?.let { act ->
            act.finishAffinity()
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun showUpdateDialog() {
        PopupHelper.createPopUp(
            requireContext(),
            PopupData(
                title = getString(R.string.app_update_title),
                message = getString(R.string.app_update_message),
                buttonTitle = getString(R.string.confirm),
                buttonFun = {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.addCategory(Intent.CATEGORY_DEFAULT)
                    intent.data = Uri.parse("market://details?id=" + getString(R.string.app_packge_name))
                    startActivity(intent)
                    it.dismiss()
                }
            )
        ).show()
    }
}
