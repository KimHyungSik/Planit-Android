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
        var adRequest = AdRequest.Builder().build()
        adRequest = AdRequest.Builder().build()
    }

    private fun InterstitialAdLoad(
        context: Context,
        adId : String,
        onFailedLoad: (() -> Unit)? = null,
        onAdLoaded: (() -> Unit)? = null,
        ){
        InterstitialAd.load(context,adId, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.message)
                if (onFailedLoad != null) {
                    onFailedLoad()
                }
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                if (onAdLoaded != null) {
                    onAdLoaded()
                }
                mInterstitialAd = interstitialAd
            }
        })
    }

    private fun InterstitialAdCallback(
        onAdDismissed: (()->Unit)? = null,
        onAdFailedShow: (()->Unit)? = null,
        onAdShowed: (()->Unit)? = null
    ){
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad was dismissed.")
                if (onAdDismissed != null) {
                    onAdDismissed()
                }
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d(TAG, "Ad failed to show.")
                if (onAdFailedShow != null) {
                    onAdFailedShow()
                }
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad showed fullscreen content.")
                if (onAdShowed != null) {
                    onAdShowed()
                }
                mInterstitialAd = null
            }
        }
    }

    private fun InterstitialAdSHow(
        activity: Activity,
        onFailedLoad: (() -> Unit)?
        ){
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(activity)
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
            if(onFailedLoad != null)
                onFailedLoad()
        }
    }

}