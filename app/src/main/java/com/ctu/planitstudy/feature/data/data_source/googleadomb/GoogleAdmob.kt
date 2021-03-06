package com.ctu.planitstudy.feature.data.data_source.googleadomb

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class GoogleAdmob {

    val TAG = "GoogleAdmob - 로그"

    private var mInterstitialAd: InterstitialAd? = null
    private var adRequest: AdRequest? = null

    init {
        adRequest = AdRequest.Builder().build()
    }

    fun getInterstitialAd() = mInterstitialAd

    fun InterstitialAdLoad(
        context: Context,
        adId: String,
        onFailedLoad: (() -> Unit)? = null,
        onAdLoadedFun: (() -> Unit)? = null,
    ) {
        InterstitialAd.load(
            context, adId, adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError?.message)
                    if (onFailedLoad != null) {
                        onFailedLoad()
                    }
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    if (onAdLoadedFun != null) {
                        onAdLoadedFun()
                    }
                }
            }
        )
    }

    fun InterstitialAdCallback(
        onAdDismissed: (() -> Unit)? = null,
        onAdFailedShow: (() -> Unit)? = null,
        onAdShowed: (() -> Unit)? = null
    ) {
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                if (onAdDismissed != null) {
                    onAdDismissed()
                }
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                if (onAdFailedShow != null) {
                    onAdFailedShow()
                }
            }

            override fun onAdShowedFullScreenContent() {
                if (onAdShowed != null) {
                    onAdShowed()
                }
                mInterstitialAd = null
            }
        }
    }

    fun InterstitialAdShow(
        activity: Activity,
        onFailedLoad: (() -> Unit)? = null
    ) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(activity)
        } else {
            if (onFailedLoad != null)
                onFailedLoad()
        }
    }
}
