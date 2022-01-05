package com.ctu.planitstudy.feature.data.data_source.googleadomb

sealed class GoogleAdType(val adId: String) {
    object FullPage : GoogleAdType("ca-app-pub-8059978824937636/7835021786")
    object Rewarded : GoogleAdType("ca-app-pub-8059978824937636/6396643570")
}
