package com.ctu.planitstudy.core.util.network

import android.content.Intent
import com.ctu.planitstudy.feature.domain.use_case.auth.JwtTokenRefreshUseCase
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.login.LoginScreen
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.net.HttpURLConnection
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val jwtTokenRefreshUseCase: JwtTokenRefreshUseCase
    )
    : Authenticator{
    val jwtRefreshTokenExpiration = JWTRefreshTokenExpiration()

    @DelicateCoroutinesApi
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            if (CashStudyApp.prefs.refreshToken!!.isNotEmpty() && !jwtRefreshTokenExpiration()) {
                runBlocking {
                    jwtTokenRefreshUseCase()
                    delay(800)
                    return@runBlocking
                }
                return makeRequest(response)
            } else {

                Intent(CashStudyApp.instance, LoginScreen::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or
                            Intent.FLAG_ACTIVITY_NEW_TASK
                }.also { CashStudyApp.instance.startActivity(it) }
            }
        }
        return null
    }

    fun makeRequest(response: Response): Request {
        return response.request
            .newBuilder()
            .removeHeader("Authorization")
            .addHeader("Authorization", CashStudyApp.prefs.accessToken!!)
            .build()
    }
}
