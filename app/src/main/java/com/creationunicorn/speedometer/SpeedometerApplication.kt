package com.creationunicorn.speedometer

import android.app.Application
import com.creationunicorn.speedometer.ads.AdManager

class SpeedometerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AdManager.initialize(this)
    }
}
