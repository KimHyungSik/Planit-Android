package com.ctu.planitstudy.feature.presentation.home.fragment.my.termsofservicedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctu.planitstudy.R
import com.ctu.planitstudy.databinding.FragmentTermsOfServiceDetailBinding

class TermsOfServiceDetail : Fragment() {

    private var _binding: FragmentTermsOfServiceDetailBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTermsOfServiceDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}