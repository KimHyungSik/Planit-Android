package com.ctu.planitstudy.feature.presentation.edituser

import com.ctu.planitstudy.feature.domain.model.user.EditUser

data class EditUserState(
    val editUser: EditUser,
    val nickNameValidate: Boolean
)
