package com.ctu.planitstudy.feature.presentation.edituser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.util.enums.findCategoryFromView
import com.ctu.planitstudy.feature.domain.model.user.EditUser
import com.ctu.planitstudy.feature.domain.use_case.user.GetUserUseCase
import com.ctu.planitstudy.feature.domain.use_case.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EditUserViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    val TAG = "EditUserViewModel - 로그"

    private val _editUserState = MutableLiveData<EditUserState>()
    val editUserState: LiveData<EditUserState> = _editUserState

    fun updateEditUser(editUser: EditUser) {
        _editUserState.value = editUserState.value!!.copy(editUser = editUser)
    }

    fun updateCategory(view: Int) {
        updateEditUser(editUserState.value!!.editUser.copy(category = findCategoryFromView(view)!!.category))
    }

    fun checkNickNameValidate() {
        userUseCase.validateNickNameUseCase(editUserState.value!!.editUser.nickname).onEach {
            when (it) {
                is Resource.Success -> {
                    _editUserState.value = editUserState.value!!.copy(nickNameValidate = it.data!!)
                }
                is Resource.Error -> {
                    Log.d(TAG, "checkNickNameValidate: ${it.message}")
                }
                is Resource.Loading -> {
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateUserName(name: String) {
        _editUserState.value =
            editUserState.value!!.copy(editUser = editUserState.value!!.editUser.copy(name = name))
    }

    fun updateUserNickName(nickname: String) {
        _editUserState.value =
            editUserState.value!!.copy(editUser = editUserState.value!!.editUser.copy(nickname = nickname))
    }
}
