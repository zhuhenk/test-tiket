package com.hendi.test_tiket

import android.app.Application
import com.hendi.test_tiket.di.AppComponent
import com.hendi.test_tiket.di.DaggerAppComponent

class App : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
    }
}