package com.ctu.planitstudy.core.util

import android.content.Context
import android.content.SharedPreferences
import com.ctu.planitstudy.core.util.CoreData.PREFERENCES_NAME
import javax.inject.Inject

class PreferencesManager @Inject constructor(
    private val context: Context
) {

    private fun getPreferences () : SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    //String 데이터 저장
    fun setString( key : String, value : String){
        val prefs = getPreferences()
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    //String 데이터 가져오기
    fun getString ( key : String) : String{
        val prefs = getPreferences()
        val value = prefs.getString(key, "")
        return value ?: ""
    }

    fun removeKey(key : String){
        val prefs = getPreferences()
        val editor = prefs.edit()
        editor.remove(key)
        editor.apply()
    }
}