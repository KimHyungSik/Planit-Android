package com.ctu.planitstudy.feature.domain.use_case.auth

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.ctu.core.util.Resource
import com.ctu.planitstudy.R
import com.ctu.planitstudy.feature.domain.model.RefreshToken
import com.ctu.planitstudy.feature.domain.repository.AuthRepository
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.common.popup.PopupData
import com.ctu.planitstudy.feature.presentation.common.popup.PopupHelper
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

class JwtTokenRefreshUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    val TAG = "JwtTokenRefreshUseCase - 로그"
    @DelicateCoroutinesApi
    operator fun invoke(): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading<String>(null))
            val json = authRepository.refreshAccessToken(RefreshToken(CashStudyApp.prefs.refreshToken!!))
            if(json.isSuccessful){
                json.body()?.let{
                    CashStudyApp.prefs.accessToken = it.asJsonObject["accessToken"].asString!!
                    emit(Resource.Success(it.asJsonObject["accessToken"].asString!!))
                }
            }else{
                if(json.code() == 412){
                   emit(Resource.AppUpdate<String>())
                }
            }
        } catch (e: Exception) {
            if (e is HttpException) {
                if (e.code() == 401)
                    emit(Resource.Error<String>(data = null, message = "토큰 만료"))
                if (e.code() == 412){
                    Log.d(TAG, "message = \"앱 버전 이상\"")
                    emit(Resource.Error<String>(data = null, message = "앱 버전 이상"))
                }
            }
            emit(Resource.Error<String>(data = null, message = "통신 실패"))
        }
    }

    private fun showUpdateDialog() {
        val context = CashStudyApp.instance
        Log.d(TAG, "showUpdateDialog: $context")
        PopupHelper.createPopUp(
            context,
            PopupData(
                title = context.getString(R.string.app_update_title),
                message = context.getString(R.string.app_update_message),
                buttonTitle = context.getString(R.string.confirm),
                buttonFun = {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.addCategory(Intent.CATEGORY_DEFAULT)
                    intent.data = Uri.parse("market://details?id=" + context.getString(R.string.app_packge_name))
                    context.startActivity(intent)
                    it.dismiss()
                }
            )
        ).show()
    }
}
