package ru.itis.morefy.core.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.itis.morefy.core.presentation.AuthActivity
import ru.itis.morefy.core.presentation.MainActivity
import ru.itis.morefy.core.presentation.fragments.ProfileFragment
import ru.itis.morefy.core.presentation.fragments.SettingsFragment
import ru.itis.morefy.statistics.presentation.StatisticsFragment
import ru.itis.morefy.statistics.presentation.tabs.OverallStatsFragment
import ru.itis.morefy.statistics.presentation.tabs.TopArtistsFragment
import ru.itis.morefy.statistics.presentation.tabs.TopTracksFragment
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(authActivity: AuthActivity)

    fun inject(statisticsFragment: StatisticsFragment)
    fun inject(overallStatsFragment: OverallStatsFragment)
    fun inject(topArtistsFragment: TopArtistsFragment)
    fun inject(topTracksFragment: TopTracksFragment)

    fun inject(profileFragment: ProfileFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}
