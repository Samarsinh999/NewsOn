package com.samar.newsontap

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.samar.newsontap.NotifyDevice.Companion.NOTIFICATION_ACTION
import com.samar.newsontap.NotifyDevice.Companion.NOTIFICATION_ACTION_EXIT
import com.samar.newsontap.NotifyDevice.Companion.NOTIFICATION_ACTION_KEY
import kotlin.system.exitProcess

class ServiceNotify: Service() {
    private val notificationDeviceState = NotifyDevice()
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == NOTIFICATION_ACTION && intent.extras?.getString(
                NOTIFICATION_ACTION_KEY
            ) == NOTIFICATION_ACTION_EXIT
        ) {
            stopForeground(true)
            exitProcess(9)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("ForegroundServiceType")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        Log.e("Serrvice Started", "Started")
        startForeground(
            NotifyDevice.NOTIFICATION_ID,
            notificationDeviceState.getNotificationBuilder(applicationContext).build()
        )
    }

    private val binder = ServiceBinder()
    override fun onBind(intent: Intent): IBinder = binder
    inner class ServiceBinder : Binder() {
        val service
            get() = this@ServiceNotify
    }
}