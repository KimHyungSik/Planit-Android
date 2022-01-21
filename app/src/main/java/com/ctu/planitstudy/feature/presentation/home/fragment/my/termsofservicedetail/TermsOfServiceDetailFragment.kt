package com.ctu.planitstudy.feature.presentation.home.fragment.my.termsofservicedetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ctu.planitstudy.R
import com.ctu.planitstudy.databinding.FragmentTermsOfServiceDetailBinding
import com.ctu.planitstudy.feature.presentation.common.action_bar.ToolBarHelper
import com.ctu.planitstudy.feature.presentation.home.HomeScreen
import com.ctu.planitstudy.feature.presentation.home.fragment.my.termsofservicedetail.adapter.TermsOfServiceAdapter

class TermsOfServiceDetailFragment : Fragment() {

    companion object {
        val TITLE = "서비스 이용약관"
        val TAG = "TermsOfServiceDetailFragment - 로그"
    }

    private var _binding: FragmentTermsOfServiceDetailBinding? = null
    private val binding get() = _binding!!
    private val termsOfServiceAdapter: TermsOfServiceAdapter by lazy {
        TermsOfServiceAdapter(){ ar -> clickTermsOfService(ar)}
    }

    private val termsOfServiceList = listOf<TermsOfServiceList>(
        TermsOfServiceList("이용약관 필수동의(필수)", "https://assets.planit-study.com/1.usage_agreements_required.pdf"),
        TermsOfServiceList("개인정보 수집 이용 동의(필수)", "https://assets.planit-study.com/2.personal_information_agreement_required.pdf"),
        TermsOfServiceList("개인정보 수집 이용 동의(선택)", "https://assets.planit-study.com/3.personal_information_agreement_select.pdf"),
        TermsOfServiceList("플래닛 알림 및 광고메시지 수신(선택)", "https://assets.planit-study.com/4.alert_agreement_select.pdf"),
        TermsOfServiceList("탈퇴하기", ""),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTermsOfServiceDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        showToolbar()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.termsOfServiceRecyclerView) {
            adapter = termsOfServiceAdapter
        }

        termsOfServiceAdapter.setList(termsOfServiceList)

        bottomNavController(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        (activity as AppCompatActivity)?.let { act ->
            ToolBarHelper.hideToolbar(act)
        }
        bottomNavController(true)
    }

    fun showToolbar() {
        (activity as AppCompatActivity)?.let { act ->
            ToolBarHelper.showToolbarWithBackButton(
                act,
                TITLE,
                buttonListener = { findNavController().popBackStack() }
            )
        }
    }

    fun bottomNavController(visible: Boolean) {
        activity?.let { act ->
            if (act is HomeScreen) {
                act.visibleBottomNav(visible)
            }
        }
    }

    private fun clickTermsOfService(item : Int){
        if(item == termsOfServiceList.size - 1){
            findNavController().navigate(R.id.action_termsOfServiceDetail_to_withdrawalFragment)
        }
    }
}
