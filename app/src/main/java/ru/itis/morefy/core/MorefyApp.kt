package ru.itis.morefy.core

import android.app.Application
import ru.itis.morefy.core.di.AppComponent
import ru.itis.morefy.core.di.DaggerAppComponent

class MorefyApp : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}
