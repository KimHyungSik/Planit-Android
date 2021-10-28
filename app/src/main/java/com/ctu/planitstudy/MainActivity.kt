package com.ctu.planitstudy

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ctu.planitstudy.core.base.BaseActivity
import com.ctu.planitstudy.databinding.ActivityMainBinding
import com.ctu.planitstudy.feature.presentation.login.LoginScreen
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.rx
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity - 로그"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "onCreate: ")
        var disposables = CompositeDisposable()
        UserApiClient.rx.loginWithKakaoAccount(this)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ token ->
                Log.i(TAG, "로그인 성공 ${token.accessToken}")
            }, { error ->
                Log.e(TAG, "로그인 실패", error)
            }).addTo(disposables)
        disposables.clear()
    }
}