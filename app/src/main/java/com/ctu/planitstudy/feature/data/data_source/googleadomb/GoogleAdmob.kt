package com.ctu.planitstudy.feature.data.data_source.googleadomb

import android.app.Activity
import android.content.Context
import android.util.Log
import com.ctu.planitstudy.core.util.CoreData.FULL_PAGE_ADVERTISING_ID
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd

class GoogleAdmob private constructor(
    val context: Context,
    val adId: String,
    val adType: GoogleAdType
) {

    val TAG = "GoogleAdmob - 로그"

    private constructor(context : Context, builder: Builder) : this(context, builder.adId, builder.googleAdType)
    private var mInterstitialAd: InterstitialAd? = null
    private var rewardAd: RewardedAd? = null
    private var adRequest: AdRequest? = null

    init {
        adRequest = AdRequest.Builder().build()
    }

    fun getInterstitialAd() = mInterstitialAd

    class Builder(){
        var context: Context? = null
        var adId = GoogleAdType.FullPage.adId
        var googleAdType: GoogleAdType = GoogleAdType.FullPage

        fun adId(googleAdType: GoogleAdType){
            this.googleAdType = googleAdType
            this.adId = googleAdType.adId

        }
        fun build(context: Context) = GoogleAdmob(context,this)
    }

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
                Log.d(TAG, "onAdDismissedFullScreenContent: onAdDismissed")
                if (onAdDismissed != null) {
                    onAdDismissed()
                }
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d(TAG, "onAdFailedToShowFullScreenContent: onAdFailed")
                if (onAdFailedShow != null) {
                    onAdFailedShow()
                }
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "onAdShowedFullScreenContent: onAdShowed")
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
