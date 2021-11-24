package com.ctu.planitstudy.core.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.ctu.planitstudy.core.util.CoreData.ACCESSTOKEN
import com.ctu.planitstudy.core.util.CoreData.PREFERENCES_NAME
import com.ctu.planitstudy.core.util.CoreData.REFRESHTOKEN

class PreferencesManager(
    private val context: Context
) {

    private val prefs = context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)

    var accessToken: String?
        get() = prefs.getString(ACCESSTOKEN, null)
        set(value) {
            prefs.edit().putString(ACCESSTOKEN, value).apply()
        }

    var refreshToken: String?
        get() = prefs.getString(REFRESHTOKEN, null)
        set(value) {
            prefs.edit().putString(REFRESHTOKEN, value).apply()
        }
}
