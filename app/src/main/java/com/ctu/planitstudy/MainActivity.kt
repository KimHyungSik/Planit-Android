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
    }
}