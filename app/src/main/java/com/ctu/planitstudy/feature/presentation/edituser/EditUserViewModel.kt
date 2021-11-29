package com.ctu.planitstudy.feature.presentation.edituser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctu.planitstudy.feature.domain.model.user.EditUser
import com.ctu.planitstudy.feature.domain.use_case.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditUserViewModel @Inject constructor(
    val userUseCase: GetUserUseCase
) : ViewModel() {

    private val _editUser = MutableLiveData<EditUser>()
    val editUser: LiveData<EditUser> = _editUser

    fun updateEditUser(editUser: EditUser) {
        _editUser.value = editUser
    }
}
