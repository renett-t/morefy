package ru.itis.morefy.statistics.domain.service

import ru.itis.morefy.core.domain.models.Genre
import ru.itis.morefy.core.domain.models.TimeRange
import ru.itis.morefy.core.domain.models.features.AverageTracksFeatures

interface UserStatsService {
    suspend fun getCurrentUserTopGenresByTopArtists(timeRange: TimeRange): Map<Genre, Int>
    suspend fun getUserOverallListeningStats(timeRange: TimeRange): AverageTracksFeatures
}
