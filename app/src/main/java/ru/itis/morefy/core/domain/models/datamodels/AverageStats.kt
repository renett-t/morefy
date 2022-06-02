package ru.itis.morefy.core.domain.models.datamodels

import androidx.room.*
import ru.itis.morefy.core.domain.models.TimeRange
import ru.itis.morefy.core.domain.models.features.MusicalKey
import ru.itis.morefy.core.domain.models.features.MusicalMode
import java.util.*

@Entity(tableName = "average_stats")
data class AverageStats(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP")
    @TypeConverters(DateConverter::class)
    var createdAt: Date,
    @ColumnInfo(name = "time_range")
    var timeRange: TimeRange,

    @ColumnInfo(name = "acousticness")
    var acousticness: Float,
    @ColumnInfo(name = "danceability")
    var danceability: Float,
    @ColumnInfo(name = "energy")
    var energy: Float,
    @ColumnInfo(name = "instrumentalness")
    var instrumentalness: Float,
    @ColumnInfo(name = "liveness")
    var liveness: Float,
    @ColumnInfo(name = "loudness")
    var loudness: Float,
    @ColumnInfo(name = "speechiness")
    var speechiness: Float,
    @ColumnInfo(name = "valence")
    var valence: Float,

    @ColumnInfo(name = "mode")
    var mode: MusicalMode,
    @ColumnInfo(name = "key")
    var key: MusicalKey,
    @ColumnInfo(name = "tempo")
    var tempo: Float
) {
}
