package com.ctu.planitstudy.feature.presentation.terms_of_use

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.widget.CheckBox
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityTermsOfUseBinding
import com.ctu.planitstudy.feature.presentation.sign_up.SignUpScreen
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxCheckedTextView
import com.jakewharton.rxbinding2.widget.RxCompoundButton
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class TermsOfUseScreenAgrees
    : BaseBindingActivity<ActivityTermsOfUseBinding>() {

    val TAG = "TermsOfUseScreen - 로그"
    private val disposables = CompositeDisposable()
    override val bindingInflater: (LayoutInflater) -> ActivityTermsOfUseBinding =
        ActivityTermsOfUseBinding::inflate

    override fun setup() {
        disposables.add(
            RxCompoundButton.checkedChanges(binding.termsOfUseAllCheckBox)
                .subscribe({
                    Log.d(TAG, "setup: $it")
                    binding.termsOfUseItems.termsOfUseAcceptUseRequired.isChecked = it
                    binding.termsOfUseItems.termsOfUsePersonalInformationRequired.isChecked = it
                    binding.termsOfUseItems.termsOfUsePersonalInformationOptional.isChecked = it
                    binding.termsOfUseItems.termsOfUseMarketingOptional.isChecked = it
                }, {})
        )

        agreeCounter(binding.termsOfUseItems.termsOfUseAcceptUseRequired)
        agreeCounter(binding.termsOfUseItems.termsOfUsePersonalInformationRequired)

        binding.termsOfUseCheckBtn.setOnClickListener {
            val intent = Intent(applicationContext, SignUpScreen::class.java)
            intent.putExtra("text", "test")
            intent.putExtra(
                "termsOfUseAgrees",
                TermsOfUseAgrees(
                    binding.termsOfUseItems.termsOfUsePersonalInformationOptional.isChecked,
                    binding.termsOfUseItems.termsOfUseMarketingOptional.isChecked
                )
            )
            moveIntentAllClear(intent)
        }
    }

    private fun agreeCounter(view: CheckBox) {
        disposables.add(RxCompoundButton.checkedChanges(view).subscribe({

            if (binding.termsOfUseItems.termsOfUseAcceptUseRequired.isChecked && binding.termsOfUseItems.termsOfUsePersonalInformationRequired.isChecked)
                binding.termsOfUseCheckBtn.run {
                    setCardBackgroundColor(resources.getColor(R.color.white))
                    isClickable = true
                }
            else
                binding.termsOfUseCheckBtn.run {
                    setCardBackgroundColor(resources.getColor(R.color.button_disabled))
                    isClickable = false
                }
        }, {}))
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}