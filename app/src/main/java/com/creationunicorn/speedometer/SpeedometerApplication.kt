package com.creationunicorn.speedometer

import com.creation.core_pulgin.core.base.BaseCoreApplication
import com.creationunicorn.speedometer.ads.AdManager
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SpeedometerApplication : BaseCoreApplication() {
    override fun onCreate() {
        super.onCreate()
        BaseCoreApplication.initialize(this)
        FirebaseApp.initializeApp(this)
        FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = true
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.APP_OPEN, null)
        AdManager.initialize(this)
    }
}
