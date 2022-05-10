package ru.itis.morefy.core.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.itis.morefy.core.di.dependencies.FeaturesDependencies
import ru.itis.morefy.core.presentation.AuthActivity
import ru.itis.morefy.core.presentation.MainActivity
import ru.itis.morefy.core.presentation.fragments.SettingsFragment

@Component(modules = [AppModule::class])
interface AppComponent : FeaturesDependencies {
    fun inject(mainActivity: MainActivity)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(authActivity: AuthActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}
