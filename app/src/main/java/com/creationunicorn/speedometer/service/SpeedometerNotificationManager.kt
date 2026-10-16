package com.creationunicorn.speedometer.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.creationunicorn.speedometer.R
import com.creationunicorn.speedometer.ui.MainActivity
import com.creationunicorn.speedometer.ui.settings.SettingsActivity

object SpeedometerNotificationManager {

    private const val CHANNEL_QUICK_BAR_ID = "speedometer_quick_bar_channel"
    private const val CHANNEL_TRIP_ID = "speedometer_tracking_channel"

    const val QUICK_BAR_NOTIFICATION_ID = 2001
    const val TRIP_NOTIFICATION_ID = 1001

    const val ACTION_STOP_TRIP = "com.creationunicorn.speedometer.ACTION_STOP_TRIP"
    const val EXTRA_TARGET_TAB = "extra_target_tab"

    fun initChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
                ?: return

            // Quick Bar Channel (Silent / Low importance)
            val quickBarChannel = NotificationChannel(
                CHANNEL_QUICK_BAR_ID,
                "Quick Controls Bar",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Quick navigation bar to launch Speedometer screens"
                setShowBadge(false)
            }

            // Trip Tracking Channel (Ongoing tracking)
            val tripChannel = NotificationChannel(
                CHANNEL_TRIP_ID,
                "Speedometer Trip Tracking",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Live trip speed and distance tracking"
                setShowBadge(false)
            }

            manager.createNotificationChannel(quickBarChannel)
            manager.createNotificationChannel(tripChannel)
        }
    }

    /**
     * Shows the ongoing 4-button quick bar notification (Gauge, Digital, Map, Settings).
     * Controlled by the "Notification bar" setting.
     */
    fun showQuickBarNotification(context: Context) {
        initChannels(context)
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            ?: return

        val views = RemoteViews(context.packageName, R.layout.notification_quick_bar)

        // Gauge button -> MainActivity tab 0
        val gaugeIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(EXTRA_TARGET_TAB, 0)
        }
        val pGauge = PendingIntent.getActivity(
            context, 101, gaugeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.btnGauge, pGauge)

        // Digital button -> MainActivity tab 1
        val digitalIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(EXTRA_TARGET_TAB, 1)
        }
        val pDigital = PendingIntent.getActivity(
            context, 102, digitalIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.btnDigital, pDigital)

        // Map button -> MainActivity tab 2
        val mapIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(EXTRA_TARGET_TAB, 2)
        }
        val pMap = PendingIntent.getActivity(
            context, 103, mapIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.btnMap, pMap)

        // Settings button -> SettingsActivity
        val settingsIntent = Intent(context, SettingsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pSettings = PendingIntent.getActivity(
            context, 104, settingsIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.btnSetting, pSettings)

        // Open app when clicking root
        val openIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pOpen = PendingIntent.getActivity(
            context, 100, openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_QUICK_BAR_ID)
            .setSmallIcon(R.drawable.ic_notification_gauge)
            .setCustomContentView(views)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setContentIntent(pOpen)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        manager.notify(QUICK_BAR_NOTIFICATION_ID, notification)
    }

    fun hideQuickBarNotification(context: Context) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        manager?.cancel(QUICK_BAR_NOTIFICATION_ID)
    }

    /**
     * Creates or updates the Trip Tracking notification with real-time speed & distance.
     */
    fun createTripNotification(
        context: Context,
        speedStr: String = "0",
        speedUnit: String = "km/h",
        distanceStr: String = "0.0",
        distanceUnit: String = "km"
    ): android.app.Notification {
        initChannels(context)

        val views = RemoteViews(context.packageName, R.layout.notification_tracking_trip)
        views.setTextViewText(R.id.currentSpeedView, speedStr)
        views.setTextViewText(R.id.currentSpeedUnitView, speedUnit)
        views.setTextViewText(R.id.distanceView, distanceStr)
        views.setTextViewText(R.id.distanceUnitView, distanceUnit)

        // Stop intent when tapping the (x) close button
        val stopIntent = Intent(ACTION_STOP_TRIP).apply {
            setPackage(context.packageName)
        }
        val pStop = PendingIntent.getBroadcast(
            context, 202, stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.closeView, pStop)

        // Launch MainActivity when clicking notification body
        val mainIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pMain = PendingIntent.getActivity(
            context, 201, mainIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(context, CHANNEL_TRIP_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setCustomContentView(views)
            .setContentIntent(pMain)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    fun updateTripNotification(
        context: Context,
        speedStr: String,
        speedUnit: String,
        distanceStr: String,
        distanceUnit: String
    ) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            ?: return
        val notif = createTripNotification(context, speedStr, speedUnit, distanceStr, distanceUnit)
        manager.notify(TRIP_NOTIFICATION_ID, notif)
    }

    fun hideTripNotification(context: Context) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        manager?.cancel(TRIP_NOTIFICATION_ID)
    }
}
