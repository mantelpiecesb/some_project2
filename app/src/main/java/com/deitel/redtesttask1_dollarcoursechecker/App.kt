package com.deitel.redtesttask1_dollarcoursechecker

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.deitel.redtesttask1_dollarcoursechecker.Constants.CHANNEL_ID_PERIOD_WORK
import com.deitel.redtesttask1_dollarcoursechecker.di.AppComponent
import com.deitel.redtesttask1_dollarcoursechecker.di.DaggerAppComponent

/**
 * Application class to configure application before creating Activity
 */
class App() : Application() {

    lateinit var daggerComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        daggerComponent = DaggerAppComponent.create()
        daggerComponent.inject(this)
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channelPeriodic = NotificationChannel(CHANNEL_ID_PERIOD_WORK, "Period Work Request", importance)
            channelPeriodic.description = "Periodic Work"
            val notificationManager = applicationContext.getSystemService(
                NotificationManager::class.java
            )
            notificationManager!!.createNotificationChannel(channelPeriodic)
        }
    }
}

val Context.appComp: AppComponent
    get() = (applicationContext as App).daggerComponent