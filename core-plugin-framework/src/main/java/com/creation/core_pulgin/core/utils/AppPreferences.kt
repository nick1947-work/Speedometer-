package com.creation.core_pulgin.core.utils

import android.content.Context
import android.content.SharedPreferences
import com.creation.core_pulgin.core.base.BaseCoreApplication

object AppPreferences {
    private const val APP_PREFERENCES_FILE_NAME = "app_preferences"

    private fun getPreferencesInstance(): SharedPreferences? {
        val context = BaseCoreApplication.getInstance()
        return context?.getSharedPreferences(
            APP_PREFERENCES_FILE_NAME,
            Context.MODE_PRIVATE
        )
    }

    fun getBoolean(booleanKey: String): Boolean {
        try {
            val sharedPreferences: SharedPreferences = getPreferencesInstance()!!
            return sharedPreferences.getBoolean(booleanKey, false)
        }catch (e:Exception){
            return false
        }

    }

    fun putBoolean(booleanKey: String, value: Boolean): Boolean {

        try {
            val sharedPreferences: SharedPreferences = getPreferencesInstance()!!
            return sharedPreferences.edit().putBoolean(booleanKey, value).commit()
        }catch (e:Exception){
            return false
        }


    }
}