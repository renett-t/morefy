package ru.itis.morefy.statistics.domain.service

import ru.itis.morefy.core.domain.models.Genre
import ru.itis.morefy.statistics.domain.models.OverallListeningStats

interface UserStatsService {
    suspend fun getCurrentUserTopGenresByTopArtists(timeRange: String): Map<Genre, Int>
    fun getUserOverallListeningStatsUseCase(timeRange: String): OverallListeningStats
}
