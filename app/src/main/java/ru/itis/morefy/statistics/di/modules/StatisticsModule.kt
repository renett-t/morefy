package ru.itis.morefy.statistics.di.modules

import dagger.Binds
import dagger.Module
import ru.itis.morefy.statistics.data.service.UserStatsServiceImpl
import ru.itis.morefy.statistics.domain.service.UserStatsService

@Module
interface StatisticsModule {

    @Binds
    fun provideUserStatsService(impl: UserStatsServiceImpl): UserStatsService
}
