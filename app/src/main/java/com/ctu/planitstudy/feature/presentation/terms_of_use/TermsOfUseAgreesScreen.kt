package com.ctu.planitstudy.feature.presentation.terms_of_use

import android.content.Intent
import android.view.LayoutInflater
import android.widget.CheckBox
import androidx.activity.viewModels
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityTermsOfUseScreenBinding
import com.ctu.planitstudy.feature.presentation.sign_up.SignUpScreen
import com.ctu.planitstudy.feature.presentation.util.Screens
import com.jakewharton.rxbinding2.widget.RxCompoundButton
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class TermsOfUseAgreesScreen :
    BaseBindingActivity<ActivityTermsOfUseScreenBinding, TermsOfUseAgreeViewModel>() {

    val TAG = "TermsOfUseScreen - 로그"
    private val disposables = CompositeDisposable()
    override val bindingInflater: (LayoutInflater) -> ActivityTermsOfUseScreenBinding =
        ActivityTermsOfUseScreenBinding::inflate

    override fun setup() {

        binding.activity = this

        disposables.add(
            RxCompoundButton.checkedChanges(binding.termsOfUseAllCheckBox)
                .subscribe({
                    with(binding.termsOfUseItems) {
                        termsOfUseAcceptUseRequired.isChecked = it
                        termsOfUsePersonalInformationRequired.isChecked = it
                        termsOfUsePersonalInformationOptional.isChecked = it
                        termsOfUseMarketingOptional.isChecked = it
                    }
                }, {})
        )

        agreeCounter(binding.termsOfUseItems.termsOfUseAcceptUseRequired)
        agreeCounter(binding.termsOfUseItems.termsOfUsePersonalInformationRequired)

        binding.termsOfUseCheckBtn.setOnClickListener {
            if (binding.termsOfUseItems.termsOfUseAcceptUseRequired.isChecked && binding.termsOfUseItems.termsOfUsePersonalInformationRequired.isChecked) {
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
    }

    private fun agreeCounter(view: CheckBox) {
        disposables.add(
            RxCompoundButton.checkedChanges(view).subscribe({

                if (binding.termsOfUseItems.termsOfUseAcceptUseRequired.isChecked && binding.termsOfUseItems.termsOfUsePersonalInformationRequired.isChecked)
                    binding.termsOfUseCheckBtn.run {
                        setCardBackgroundColor(getColor(R.color.text_color))
                        isClickable = true
                    }
                else
                    binding.termsOfUseCheckBtn.run {
                        setCardBackgroundColor(getColor(R.color.button_disabled))
                        isClickable = false
                    }
            }, {})
        )
    }

    fun termsOfService(url: String) {
        val intent = Intent(this, Screens.TermsOfUseSh.activity)
        intent.putExtra("Url", url)
        moveIntent(intent)
    }

    fun getStringFromR(id: Int) = getString(id)

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    override val viewModel: TermsOfUseAgreeViewModel by viewModels()
}
