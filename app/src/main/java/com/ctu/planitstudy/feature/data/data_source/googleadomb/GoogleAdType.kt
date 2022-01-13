package com.ctu.planitstudy.feature.data.data_source.googleadomb

import com.ctu.planitstudy.core.util.CoreData.FULL_PAGE_ADVERTISING_ID
import com.ctu.planitstudy.core.util.CoreData.REWAARDED_ADVERTISING_ID

sealed class GoogleAdType(val adId: String) {
    object FullPage : GoogleAdType(FULL_PAGE_ADVERTISING_ID)
    object Rewarded : GoogleAdType(REWAARDED_ADVERTISING_ID)
}
