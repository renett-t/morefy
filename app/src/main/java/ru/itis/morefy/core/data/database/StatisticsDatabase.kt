package ru.itis.morefy.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.itis.morefy.core.data.database.dao.AverageStatsDao
import ru.itis.morefy.core.domain.models.datamodels.AverageStats
import ru.itis.morefy.core.domain.models.datamodels.DateConverter

@Database(
    entities = [AverageStats::class],
    version = 5,
    exportSchema = true
)
@TypeConverters(DateConverter::class)
abstract class StatisticsDatabase : RoomDatabase() {
    abstract fun averageStatsDao(): AverageStatsDao

    companion object {
        const val DATABASE_NAME = "morefy.db"
    }
}
