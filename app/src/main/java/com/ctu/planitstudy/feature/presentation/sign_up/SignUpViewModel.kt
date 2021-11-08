package com.ctu.planitstudy.feature.presentation.sign_up

import android.accounts.NetworkErrorException
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.util.CoreData.ACCESSTOKEN
import com.ctu.planitstudy.core.util.CoreData.REFRESHTOKEN
import com.ctu.planitstudy.core.util.PreferencesManager
import com.ctu.planitstudy.feature.data.data_source.user.UserManager
import com.ctu.planitstudy.feature.data.remote.dto.JsonConverter
import com.ctu.planitstudy.feature.domain.model.SignUpUser
import com.ctu.planitstudy.feature.domain.model.SignUpUserReceiver
import com.ctu.planitstudy.feature.domain.model.SignUpUserResponse
import com.ctu.planitstudy.feature.domain.use_case.user.UserAuthUseCase
import com.ctu.planitstudy.feature.domain.use_case.user.UserValidateNickNameUseCase
import com.ctu.planitstudy.feature.presentation.sign_up.fragment.SignUpFragments
import com.ctu.planitstudy.feature.presentation.terms_of_use.TermsOfUseAgrees
import com.ctu.planitstudy.feature.presentation.util.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.adapter.rxjava2.Result.response
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userManager: UserManager,
    private val userAuthUseCase: UserAuthUseCase,
    private val userValidateNickNameUseCase: UserValidateNickNameUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    val TAG = "SignUpViewModel - 로그"

    // 현재 회원 가입 정보
    private val _livedata = MutableLiveData<SignUpState>(SignUpState())
    val liveData: LiveData<SignUpState> = _livedata

    // 화면 전화 여부 확인
    private val _activityState = MutableLiveData<Boolean>(false)
    val activityState: LiveData<Boolean> = _activityState

    // 현재 표시 중인 화면
    private val _signUpFragments = MutableLiveData<SignUpFragments>(SignUpFragments.Name)
    val signUpFragments: LiveData<SignUpFragments> = _signUpFragments

    private val _signUpUserResponse = MutableLiveData<SignUpUserResponse>()
    val `signUpUserResponse`: LiveData<SignUpUserResponse> = _signUpUserResponse

    private val _validateNickName =
        MutableLiveData<Resource<Boolean>>(Resource.Error(data = false, message = "init"))
    val validateNickName: LiveData<Resource<Boolean>> = _validateNickName

    private val _screens= MutableLiveData<Screens>(null)
    val screens : LiveData<Screens> = _screens


    var currentFragmentPage = 0
    var maxFragmentPage = 0

    var termsOfUseAgrees: TermsOfUseAgrees = TermsOfUseAgrees(
        personalInformationAgree = false,
        marketingInformationAgree = false
    )

    val fragmentsList = listOf(
        SignUpFragments.Name,
        SignUpFragments.Gender,
        SignUpFragments.DateOfBirth,
        SignUpFragments.Category,
        SignUpFragments.ReceiverName
    )

    init {
        // 현재 회원가입 상태 별로 화면 전환 검사
        liveData.observeForever { signUpState ->
            var pageCount = 0
            pageCount += if (signUpState.nickname.isNotBlank() && signUpState.name.isNotBlank()) 1 else 0
            pageCount += if (signUpState.gender.isNotBlank()) 1 else 0
            pageCount += if (signUpState.dateOfBirth.isNotBlank() && signUpState.dateFormat) 1 else 0
            pageCount += if (signUpState.category.isNotBlank()) 1 else 0
            pageCount += if (signUpState.receiverName.isNotBlank()) 1 else 0
            _activityState.value = currentFragmentPage < pageCount
        }
    }

    private fun validateNickNameCheck() {
        userValidateNickNameUseCase(liveData.value?.nickname!!).onEach { result ->
            _validateNickName.value = result
            when (result) {
                is Resource.Success -> {
                    Log.d(TAG, "validateNickNameCheck: success ${result.data}")
                    _activityState.value = result.data!!
                }
                is Resource.Error -> {
                    Log.d(TAG, "validateNickNameCheck: error ${result.message}")
                }
                is Resource.Loading -> {
                    Log.d(TAG, "validateNickNameCheck: loading")
                    _activityState.value = false
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateSignState(state: SignUpState) {
        _livedata.value = state
    }

    fun validateNickNameStateChange(state: Boolean) {
        _validateNickName.value = Resource.Error<Boolean>(data = state, message = "")
    }

    fun fragmentPageChange(i: Int) {
        if (currentFragmentPage > 0 && currentFragmentPage < fragmentsList.size) {
            currentFragmentPage += i
            _signUpFragments.value = (fragmentsList[currentFragmentPage])
            _activityState.value = true
        }
    }

    fun checkSignUpUserData() {
        if (signUpFragments.value == SignUpFragments.Name && !validateNickName.value?.data!!) {
            validateNickNameCheck()
        }
        if (_activityState.value!!) {
            _activityState.value = false
            _signUpFragments.value = fragmentsList[++currentFragmentPage]
            maxFragmentPage = currentFragmentPage.coerceAtLeast(maxFragmentPage)

            if (maxFragmentPage > currentFragmentPage)
                _activityState.value = true
        }

    }

    fun sendSignUpUserData(receiverNameSkip: Boolean) {
        userManager.getUserInfo()
            .subscribe({ it ->
                val signUpUser = SignUpUser(
                    birth = liveData.value?.dateOfBirth!!,
                    category = liveData.value?.category!!,
                    email =  it.data?.userEmail!!,
                    marketingInformationAgree = termsOfUseAgrees.marketingInformationAgree,
                    personalInformationAgree = termsOfUseAgrees.personalInformationAgree,
                    name = liveData.value?.name!!,
                    nickname = liveData.value?.nickname!!,
                    sex = liveData.value?.gender!!
                )

                val signUpUserReceiver = SignUpUserReceiver(
                    birth = liveData.value?.dateOfBirth!!,
                    category = liveData.value?.category!!,
                    email = it.data?.userEmail!!,
                    marketingInformationAgree = termsOfUseAgrees.marketingInformationAgree,
                    personalInformationAgree = termsOfUseAgrees.personalInformationAgree,
                    name = liveData.value?.name!!,
                    nickname = liveData.value?.nickname!!,
                    receiverNickname = liveData.value?.receiverName!!,
                    sex = liveData.value?.gender!!,
                )
                Log.d(TAG, "sendSignUpUserData: $signUpUserReceiver")
                (
                        if (receiverNameSkip)
                            userAuthUseCase.userSignUp(signUpUserReceiver)
                        else
                            userAuthUseCase.userSignUp(signUpUser)
                        )
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { JsonConverter.jsonToSignUpUserDto(it.asJsonObject) }
                    .subscribe({
                        _signUpUserResponse.value = SignUpUserResponse(
                            200,
                            accessToken = it.accessToken,
                            refreshToken = it.refreshToken
                        )
                        preferencesManager.setString(ACCESSTOKEN, it.accessToken)
                        preferencesManager.setString(REFRESHTOKEN, it.refreshToken)
                        _screens.value = Screens.HomeScreenSh()
                    }, {
                        if (it is HttpException) {
                            val jObjError = JSONObject(it.response()!!.errorBody()!!.string())
                            Log.i(TAG, "sendSignUpUserData: ${jObjError}")
                            _signUpUserResponse.value = SignUpUserResponse(
                                it.response()!!.code(),
                                accessToken = "",
                                refreshToken = ""
                            )
                        }
                    })
            }, {
                _signUpUserResponse.value = SignUpUserResponse(accessToken = "", refreshToken = "")
            }).isDisposed

    }
}