package com.ctu.planitstudy.feature.presentation.edituser

import android.view.LayoutInflater
import android.widget.RadioButton
import androidx.activity.viewModels
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.core.util.enums.findCategoryFromString
import com.ctu.planitstudy.core.util.enums.findCategoryFromView
import com.ctu.planitstudy.databinding.ActivityEditUserScreenBinding
import com.ctu.planitstudy.feature.data.remote.dto.user.UserInformationDto
import com.ctu.planitstudy.feature.domain.model.user.EditUser
import com.jakewharton.rxbinding2.widget.RxRadioGroup
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class EditUserScreen : BaseBindingActivity<ActivityEditUserScreenBinding>() {

    val TAG = "EditUserScreen - 로그"

    override val bindingInflater: (LayoutInflater) -> ActivityEditUserScreenBinding
        get() = ActivityEditUserScreenBinding::inflate
    var user: UserInformationDto? = null

    private val disposables = CompositeDisposable()

    private val viewModel: EditUserViewModel by viewModels()

    override fun setup() {
        user = intent.getParcelableExtra<UserInformationDto>("user")
        binding.activity = this

        with(viewModel) {
            updateEditUser(
                EditUser(
                    category = user!!.category,
                    name = user!!.name,
                    nickname = user!!.nickname
                )
            )
        }

        val checkCategory =
            findViewById<RadioButton>(findCategoryFromString(user!!.category)!!.editUser)
        checkCategory.isChecked = true

        disposables.add(
            RxRadioGroup.checkedChanges(binding.editUserCategoryRadioGroupLeft)
                .filter { it -> it != -1 }
                .subscribe({
                    binding.editUserCategoryRadioGroupRight.clearCheck()
                    val category: String = findCategoryFromView(it)!!.category
                }, {})
        )

        disposables.add(
            RxRadioGroup.checkedChanges(binding.editUserCategoryRadioGroupRight)
                .filter { it -> it != -1 }
                .subscribe({
                    binding.editUserCategoryRadioGroupLeft.clearCheck()
                    val category: String = findCategoryFromView(it)!!.category
                }, {})
        )
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}
