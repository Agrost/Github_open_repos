package com.example.githubopenrepos

import android.app.Application
import android.content.Context
import com.example.githubopenrepos.di.AppComponent
import com.example.githubopenrepos.di.DaggerAppComponent

class MainApp : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is MainApp -> appComponent
        else -> applicationContext.appComponent
    }