package ru.itis.morefy.core.data.database.dao

import androidx.room.*
import ru.itis.morefy.core.domain.models.datamodels.AverageStats
import java.util.*

@Dao
abstract class AverageStatsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun save(entity: AverageStats)

    suspend fun saveWithTimestamp(entity: AverageStats) {
        save(entity.apply {
            createdAt = Date()
        })
    }

    @Update
    abstract suspend fun updateAverageStats(vararg entities: AverageStats)

    @Query("SELECT * FROM average_stats WHERE id = :id")
    abstract suspend fun getStatsById(id: Int): AverageStats?

    @Query("SELECT * FROM average_stats")
    abstract suspend fun getAllStats(): List<AverageStats>

    @Query("SELECT * FROM average_stats WHERE created_at <= :time")
    abstract suspend fun getStatsBeforeTimeStamp(time: Long): List<AverageStats>

    @Query("SELECT * FROM average_stats WHERE created_at >= :time")
    abstract suspend fun getStatsAfterTimeStamp(time: Long): List<AverageStats>

    @Delete
    abstract suspend fun deleteAverageStats(vararg entities: AverageStats)

    @Query("DELETE FROM average_stats")
    abstract suspend fun deleteAllStats()
}
