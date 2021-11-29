package com.ctu.planitstudy.feature.presentation.edituser

import android.view.LayoutInflater
import android.widget.RadioButton
import androidx.activity.viewModels
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.core.util.enums.findCategoryFromString
import com.ctu.planitstudy.core.util.isValidText
import com.ctu.planitstudy.core.util.setColor
import com.ctu.planitstudy.databinding.ActivityEditUserScreenBinding
import com.ctu.planitstudy.feature.data.remote.dto.user.UserInformationDto
import com.ctu.planitstudy.feature.domain.model.user.EditUser
import com.jakewharton.rxbinding2.widget.RxRadioGroup
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class EditUserScreen : BaseBindingActivity<ActivityEditUserScreenBinding>() {

    val TAG = "EditUserScreen - 로그"

    override val bindingInflater: (LayoutInflater) -> ActivityEditUserScreenBinding
        get() = ActivityEditUserScreenBinding::inflate
    var user: UserInformationDto? = null

    private val disposables = CompositeDisposable()
    private val viewModel: EditUserViewModel by viewModels()
    private lateinit var editUser: EditUser

    override fun setup() {
        user = intent.getParcelableExtra<UserInformationDto>("user")
        binding.activity = this

        editUser = EditUser(
            category = user!!.category,
            name = user!!.name,
            nickname = user!!.nickname
        )

        with(viewModel) {
            updateEditUser(
                this@EditUserScreen.editUser
            )
            editUserState.observe(this@EditUserScreen, {
                if (this@EditUserScreen.editUser == it.editUser && it.nickNameValidate) {
                    with(binding) {
                        editUserConfirmBtn.setCardBackgroundColor(setColor(R.color.enabled_confirm_btn))
                        editUserConfirmText.setTextColor(setColor(R.color.navy_blue_item_box))
                    }
                } else {
                    with(binding) {
                        editUserConfirmBtn.setCardBackgroundColor(setColor(R.color.white))
                        editUserConfirmText.setTextColor(setColor(R.color.item_black))
                    }
                }
            })
        }

        val checkCategory =
            findViewById<RadioButton>(findCategoryFromString(user!!.category)!!.editUser)
        checkCategory.isChecked = true

        disposables.add(
            RxRadioGroup.checkedChanges(binding.editUserCategoryRadioGroupLeft)
                .filter { it -> it != -1 }
                .subscribe({
                    binding.editUserCategoryRadioGroupRight.clearCheck()
                    viewModel.updateCategory(it)
                }, {})
        )

        disposables.add(
            RxRadioGroup.checkedChanges(binding.editUserCategoryRadioGroupRight)
                .filter { it -> it != -1 }
                .subscribe({
                    binding.editUserCategoryRadioGroupLeft.clearCheck()
                    viewModel.updateCategory(it)
                }, {})
        )

        disposables.add(
            RxTextView.textChanges(binding.editUserNicknameText)
                .filter { it.isNotBlank() }
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribe({
                    if (!!it.toString().isValidText()) {
                    }
                }, {}, {})
        )
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}
