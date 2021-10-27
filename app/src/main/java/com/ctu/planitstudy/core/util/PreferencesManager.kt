package com.ctu.planitstudy.core.util

import android.content.Context
import android.content.SharedPreferences
import com.ctu.planitstudy.core.util.CoreData.PREFERENCES_NAME

object PreferencesManager {

    private fun getPreferences (context : Context) : SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    //String 데이터 저장
    fun setString(context : Context, key : String, value : String){
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    //String 데이터 가져오기
    fun getString (context : Context, key : String) : String{
        val prefs = getPreferences(context)
        val value = prefs.getString(key, "")
        return value ?: ""
    }

    fun removeKey(context : Context, key : String){
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.remove(key)
        editor.apply()
    }
}