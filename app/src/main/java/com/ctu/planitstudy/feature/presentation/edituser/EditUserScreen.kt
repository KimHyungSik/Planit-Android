package com.ctu.planitstudy.feature.presentation.edituser

import android.view.LayoutInflater
import android.view.View
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
import com.ctu.planitstudy.feature.presentation.util.Screens
import com.jakewharton.rxbinding2.widget.RxRadioGroup
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class EditUserScreen : BaseBindingActivity<ActivityEditUserScreenBinding, EditUserViewModel>() {

    val TAG = "EditUserScreen - 로그"

    override val bindingInflater: (LayoutInflater) -> ActivityEditUserScreenBinding
        get() = ActivityEditUserScreenBinding::inflate
    var user: UserInformationDto? = null

    private val disposables = CompositeDisposable()
    override val viewModel: EditUserViewModel by viewModels()
    private lateinit var editUser: EditUser

    override fun setup() {
        user = intent.getParcelableExtra<UserInformationDto>("user")
        binding.activity = this
        binding.viewmodel = viewModel

        editUser = EditUser(
            category = user!!.category,
            name = user!!.name,
            nickname = user!!.nickname
        )

        with(viewModel) {
            updateEditUser(
                this@EditUserScreen.editUser
            )
            editUserState.observe(
                this@EditUserScreen,
                {

                    if (it.edit)
                        moveIntentAllClear(Screens.HomeScreenSh.activity)

                    if (this@EditUserScreen.editUser == it.editUser || it.editUser.category.isBlank()) {
                        with(binding) {
                            editUserConfirmBtn.setCardBackgroundColor(setColor(R.color.enabled_confirm_btn))
                            editUserConfirmText.setTextColor(setColor(R.color.navy_blue_item_box))
                            editUserConfirmBtn.isCheckable = false
                        }
                    } else {
                        if (this@EditUserScreen.editUser.nickname != it.editUser.nickname && !it.nickNameValidate) {
                            with(binding) {
                                editUserNicknameErrorText.text = "이미 사용중인 닉네임입니다."
                                editUserNicknameErrorIcon.visibility = View.VISIBLE
                                editUserConfirmBtn.isCheckable = false
                            }
                        } else
                            with(binding) {
                                editUserNicknameErrorIcon.visibility = View.INVISIBLE
                                editUserConfirmBtn.setCardBackgroundColor(setColor(R.color.white))
                                editUserConfirmText.setTextColor(setColor(R.color.item_black))
                                editUserConfirmBtn.isCheckable = true
                            }
                    }
                }
            )
        }

        val checkCategory =
            findViewById<RadioButton>(findCategoryFromString(user!!.category)!!.editUser)
        checkCategory.isChecked = true

        // 닉네임 텍스트 변경
        disposables.addAll(
            RxTextView.textChanges(binding.editUserNicknameText)
                .filter { it.isNotBlank() }
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribe(
                    {
                        CoroutineScope(Dispatchers.Main + mainJob).launch {
                            with(binding.editUserNicknameErrorText) {
                                text = ""
                                if (!it.toString().isValidText()) {
                                    text = "닉네임 형식이 올바르지 않습니다."
                                    binding.editUserNicknameErrorIcon.visibility = View.VISIBLE
                                    binding.editUserConfirmBtn.isCheckable = false
                                } else {
                                    viewModel.checkNickNameValidate(
                                        it.toString(),
                                        this@EditUserScreen.editUser.nickname
                                    )
                                }
                            }
                        }
                    },
                    {
                    },
                    {
                    }
                ),
            // 카테고리 선택 라디오 버튼
            RxRadioGroup.checkedChanges(binding.editUserCategoryRadioGroupRight)
                .filter { it != -1 }
                .subscribe(
                    {
                        binding.editUserCategoryRadioGroupLeft.clearCheck()
                        viewModel.updateCategory(it)
                    },
                    {}
                ),
            RxRadioGroup.checkedChanges(binding.editUserCategoryRadioGroupLeft)
                .filter { it != -1 }
                .subscribe(
                    {
                        binding.editUserCategoryRadioGroupRight.clearCheck()
                        viewModel.updateCategory(it)
                    },
                    {}
                )
        )
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}
