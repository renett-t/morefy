package ru.itis.morefy.statistics.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import dagger.Component
import ru.itis.morefy.statistics.di.modules.StatisticsModule
import ru.itis.morefy.statistics.presentation.StatisticsFragment
import ru.itis.morefy.statistics.presentation.tabs.OverallStatsFragment
import ru.itis.morefy.statistics.presentation.tabs.TopArtistsFragment
import ru.itis.morefy.statistics.presentation.tabs.TopTracksFragment
import kotlin.properties.Delegates.notNull

@StatisticsScope
@Component(
    modules = [
        StatisticsModule::class,
    ],
    dependencies = [
        StatisticsDependencies::class,
    ],
)
internal interface StatisticsComponent {

    fun inject(statisticsFragment: StatisticsFragment)
    fun inject(overallStatsFragment: OverallStatsFragment)
    fun inject(topArtistsFragment: TopArtistsFragment)
    fun inject(topTracksFragment: TopTracksFragment)

    @Component.Builder
    interface Builder {
        fun dependencies(statisticsDependencies: StatisticsDependencies): Builder
        fun build(): StatisticsComponent
    }
}

internal class StatisticsComponentViewModel : ViewModel() {

    val statisticsComponent = DaggerStatisticsComponent.builder()
                                .dependencies(StatisticsDependenciesProvider.dependencies)
                                .build()
}

// todo: is this way of providing sub-components ok?
interface StatisticsDependenciesProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val dependencies: StatisticsDependencies

    companion object : StatisticsDependenciesProvider by DependenciesStore
}

object DependenciesStore : StatisticsDependenciesProvider {

    override var dependencies: StatisticsDependencies by notNull()
}

