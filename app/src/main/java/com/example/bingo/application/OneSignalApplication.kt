package com.example.bingo.application

import android.app.Application
import com.onesignal.OneSignal

class OneSignalApplication:Application() {

    val ONESIGNALL_APP_ID = "2e534ae3-a2b3-4db3-9f37-3c153cd240d1"

    override fun onCreate() {
        super.onCreate()
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNALL_APP_ID)
    }

}