package com.upday.updaytask.data.preferences

import android.content.Context
import com.upday.updaytask.util.Constants

class AppPreferences(private val context: Context) : Preferences {

    override fun writeString(key: String, value: String) {
        context.getSharedPreferences(Constants.PREFERENCES_NAME, Constants.PREFERENCES_MODE)
            .edit().putString(key,value).apply()
    }

    override fun readString(key: String): String {
        return context.getSharedPreferences(Constants.PREFERENCES_NAME, Constants.PREFERENCES_MODE).getString(key, "")!!
    }
}