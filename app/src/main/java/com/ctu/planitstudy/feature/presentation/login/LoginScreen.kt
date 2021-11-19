package com.ctu.planitstudy.feature.presentation.login

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.core.util.CoreData.ACCESSTOKEN
import com.ctu.planitstudy.core.util.PreferencesManager
import com.ctu.planitstudy.core.util.network.JWTRefreshTokenExpiration
import com.ctu.planitstudy.databinding.ActivityLoginScreenBinding
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.home.HomeScreen
import com.ctu.planitstudy.feature.presentation.util.Screens.*
import dagger.hilt.android.AndroidEntryPoint
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

@AndroidEntryPoint
class LoginScreen
    : BaseBindingActivity<ActivityLoginScreenBinding>()
{
    private val viewModel: LoginViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ActivityLoginScreenBinding
        = ActivityLoginScreenBinding::inflate

    val TAG = "LoginScreen - 로그"

    override fun setup() {

        binding.viewmodel = viewModel
        
        printHashKey(CashStudyApp.instance)

        if(CashStudyApp.prefs.refreshToken != null)
            if(!JWTRefreshTokenExpiration().invoke())
                moveIntentAllClear(HomeScreenSh.activity)

        viewModel.loginState.observe(this, {
            when(it){
                is LoginState.Loading->{
                    Toast.makeText(this, "로그인 중", Toast.LENGTH_SHORT).show()
                }
                is LoginState.Login->{
                    if(it.state) {
                        moveIntentAllClear(HomeScreenSh.activity)
                    }
                    else
                        moveIntentAllClear(TermsOfUseAgreesScreenSh.activity)
                }
            }
        })
    }

   fun printHashKey(pContext: Context) {
        try {
            val info: PackageInfo = pContext.getPackageManager()
                .getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey: String = String(Base64.encode(md.digest(), 0))
                Toast.makeText(this, hashKey, Toast.LENGTH_SHORT).show()
                binding.loginScreenMainTitle.text = hashKey
                Log.i(TAG, "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "printHashKey()", e)
        } catch (e: Exception) {
            Log.e(TAG, "printHashKey()", e)
        }
    }
}
