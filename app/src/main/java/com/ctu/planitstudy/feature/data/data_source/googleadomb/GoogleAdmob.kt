package com.ctu.planitstudy.feature.data.data_source.googleadomb

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class GoogleAdmob private constructor(
    val context: Context,
    val adId: String,
    val adType: GoogleAdType
) {

    val TAG = "GoogleAdmob - 로그"

    private constructor(context: Context, builder: Builder) : this(
        context,
        builder.adId,
        builder.googleAdType
    )

    private var mInterstitialAd: InterstitialAd? = null
    private var rewardAd: RewardedAd? = null
    private var adRequest: AdRequest? = null

    init {
        adRequest = AdRequest.Builder().build()
    }

    fun getInterstitialAd(): Boolean = when (adType) {
        GoogleAdType.FullPage -> mInterstitialAd != null
        GoogleAdType.Rewarded -> rewardAd != null
    }

    class Builder() {
        var context: Context? = null
        var adId = GoogleAdType.FullPage.adId
        var googleAdType: GoogleAdType = GoogleAdType.FullPage

        fun googleAdType(googleAdType: GoogleAdType): Builder {
            this.googleAdType = googleAdType
            this.adId = googleAdType.adId
            return this
        }

        fun build(context: Context) = GoogleAdmob(context, this)
    }

    fun InterstitialAdLoad(
        onFailedLoad: (() -> Unit)? = null,
        onAdLoadedFun: (() -> Unit)? = null,
    ) {
        when (adType) {
            GoogleAdType.FullPage -> {
                InterstitialAd.load(
                    context, adId, adRequest,
                    object : InterstitialAdLoadCallback() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
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
            GoogleAdType.Rewarded -> {
                RewardedAd.load(
                    context, adId, adRequest,
                    object : RewardedAdLoadCallback() {
                        override fun onAdLoaded(rewardAd: RewardedAd) {
                            if (onAdLoadedFun != null) {
                                onAdLoadedFun()
                            }
                            this@GoogleAdmob.rewardAd = rewardAd
                        }

                        override fun onAdFailedToLoad(p0: LoadAdError) {
                            if (onFailedLoad != null) {
                                onFailedLoad()
                            }
                            this@GoogleAdmob.rewardAd = null
                        }
                    }
                )
                this@GoogleAdmob.rewardAd
            }
        }
    }

    fun InterstitialAdCallback(
        onAdDismissed: (() -> Unit)? = null,
        onAdFailedShow: (() -> Unit)? = null,
        onAdShowed: (() -> Unit)? = null
    ) {
        when (adType) {
            GoogleAdType.FullPage -> {
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
            GoogleAdType.Rewarded -> {
                rewardAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
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
                        rewardAd = null
                    }
                }
            }
        }
    }

    fun InterstitialAdShow(
        activity: Activity,
        onFailedLoad: (() -> Unit)? = null,
        onShowed: (() -> Unit)? = null
    ) {
        when (adType) {
            GoogleAdType.FullPage -> {
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(activity)
                    if (onShowed != null)
                        onShowed()
                } else {
                    if (onFailedLoad != null)
                        onFailedLoad()
                }
            }
            GoogleAdType.Rewarded -> {
                if (rewardAd != null) {
                    rewardAd?.show(
                        activity
                    ) {
                        val rewardAmount = it.amount
                        val rewardType = it.type
                        if (onShowed != null)
                            onShowed()
                    }
                } else {
                    if (onFailedLoad != null)
                        onFailedLoad()
                }
            }
        }
    }
}
