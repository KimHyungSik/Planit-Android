package com.ctu.planitstudy.feature.presentation.home.fragment.my.termsofservicedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ctu.planitstudy.R
import com.ctu.planitstudy.databinding.FragmentTermsOfServiceDetailBinding
import com.ctu.planitstudy.feature.presentation.home.fragment.my.termsofservicedetail.adapter.TermsOfServiceAdapter

class TermsOfServiceDetailFragment : Fragment() {

    private var _binding: FragmentTermsOfServiceDetailBinding? = null
    private val binding get() = _binding!!
    private val termsOfServiceAdapter: TermsOfServiceAdapter by lazy {
        TermsOfServiceAdapter()
    }

    private val termsOfServiceList = listOf<TermsOfServiceList>(
        TermsOfServiceList("이용약관 필수동의(필수)", "https://assets.planit-study.com/1.usage_agreements_required.pdf"),
        TermsOfServiceList("개인정보 수집 이용 동의(필수)", "https://assets.planit-study.com/2.personal_information_agreement_required.pdf"),
        TermsOfServiceList("개인정보 수집 이용 동의(선택)", "https://assets.planit-study.com/3.personal_information_agreement_select.pdf"),
        TermsOfServiceList("플래닛 알림 및 광고메시지 수신(선택)", "https://assets.planit-study.com/4.alert_agreement_select.pdf"),
        TermsOfServiceList("탈퇴하기", ""),
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTermsOfServiceDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.termsOfServiceRecyclerView){
            adapter = termsOfServiceAdapter
        }
        termsOfServiceAdapter.setList(termsOfServiceList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}