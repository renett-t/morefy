package ru.itis.morefy.core.presentation.extensions

import android.content.Context
import ru.itis.morefy.core.MorefyApp
import ru.itis.morefy.core.di.AppComponent

val Context.appComponent : AppComponent
    get() = when (this) {
        is MorefyApp -> appComponent
        else -> this.applicationContext.appComponent
    }
