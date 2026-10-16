package com.creation.core_pulgin.core.base

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.LocaleList
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import java.util.Locale

open class CoreBaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(updateContextWithLocale(newBase))
    }

    private fun updateContextWithLocale(context: Context): Context {
        return try {
            val language = GetSharedPreference(context, "My_Lang")
            if (language != null && language.isNotEmpty()) {
                val locale = createLocaleFromLanguage(language)
                updateResourcesWithLocale(context, locale)
            } else {
                context
            }
        } catch (e: Exception) {
            context
        }
    }

    private fun createLocaleFromLanguage(language: String): Locale {
        val arr = language.split("_").toTypedArray()
        return if (arr.size > 1) {
            Locale(arr[0], arr[1].uppercase(Locale.getDefault()))
        } else {
            Locale(language.lowercase(Locale.getDefault()))
        }
    }

    private fun updateResourcesWithLocale(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)
        
        val configuration = Configuration(context.resources.configuration)
        
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            configuration.setLocales(localeList)
            context.createConfigurationContext(configuration)
        } else {
            @Suppress("DEPRECATION")
            configuration.locale = locale
            configuration.setLayoutDirection(locale)
            context.createConfigurationContext(configuration)
        }
    }

    fun GetSharedPreference(ctx: Context, Key: String?): String? {
        val pref: SharedPreferences =
            ctx.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        return if (pref.contains(Key)) {
            pref.getString(Key, "")
        } else null
    }


    fun setFullScreenMode() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }

    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    open fun showPopupMessage(message: String?, title: String = "", buttonMessage: String = "") {
        var buttonMessage = buttonMessage
        val alertBuilder = AlertDialog.Builder(this)
        if (title != null) {
            alertBuilder.setTitle(title)
        }
        if (message != null) {
            alertBuilder.setMessage(message)
        }
        if (buttonMessage == null) {
            buttonMessage = "OK"
        }
        alertBuilder.setPositiveButton(
            buttonMessage
        ) { dialogInterface, i -> dialogInterface.dismiss() }
        alertBuilder.show()
    }


}