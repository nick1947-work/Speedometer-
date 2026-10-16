package com.creationunicorn.speedometer.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder

class TrackingService : Service() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, TrackingService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }

        fun stop(context: Context) {
            val intent = Intent(context, TrackingService::class.java)
            context.stopService(intent)
        }
    }

    private val stopReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == SpeedometerNotificationManager.ACTION_STOP_TRIP) {
                // Broadcast an event or stop directly
                val stopBroadcast = Intent("com.creationunicorn.speedometer.STOP_TRIP_FROM_NOTIF").apply {
                    setPackage(packageName)
                }
                sendBroadcast(stopBroadcast)
                stopSelf()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        val notif = SpeedometerNotificationManager.createTripNotification(this)
        startForeground(SpeedometerNotificationManager.TRIP_NOTIFICATION_ID, notif)

        val filter = IntentFilter(SpeedometerNotificationManager.ACTION_STOP_TRIP)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(stopReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(stopReceiver, filter)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        try {
            unregisterReceiver(stopReceiver)
        } catch (e: Exception) {
            // Ignored
        }
        SpeedometerNotificationManager.hideTripNotification(this)
        stopForeground(STOP_FOREGROUND_REMOVE)
    }
}
