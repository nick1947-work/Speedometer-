package com.creation.core_pulgin.core.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//@HiltAndroidApp
open class BaseCoreApplication : Application() {

    companion object {
        private var _instance: Application? = null
        fun getInstance() = _instance

        fun initialize(application: Application) {
            _instance = application
            initFirebase(application)
        }

        fun initFirebase(context: Application) {
            try {
                com.google.firebase.FirebaseApp.initializeApp(context)
                com.creation.core_pulgin.core.utils.FirebaseRemoteConfigUtils.init(context)
            } catch (e: Exception) {
                android.util.Log.e("BaseCoreApplication", "Failed to initialize Firebase", e)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        _instance = this
        initFirebase(this)
    }


}