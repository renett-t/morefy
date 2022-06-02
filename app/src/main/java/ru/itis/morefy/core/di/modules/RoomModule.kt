package ru.itis.morefy.core.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.itis.morefy.core.data.database.StatisticsDatabase
import ru.itis.morefy.core.data.database.dao.AverageStatsDao
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideDataBase(context: Context): StatisticsDatabase =
        Room.databaseBuilder(
            context,
            StatisticsDatabase::class.java,
            StatisticsDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideAverageStatsDao(db: StatisticsDatabase): AverageStatsDao = db.averageStatsDao()
}
