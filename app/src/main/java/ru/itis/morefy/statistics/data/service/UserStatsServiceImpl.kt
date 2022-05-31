package ru.itis.morefy.statistics.data.service

import ru.itis.morefy.core.data.api.MAX_LIMIT_AMOUNT
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Genre
import ru.itis.morefy.core.domain.usecase.GetArtistUseCase
import ru.itis.morefy.core.domain.usecase.GetFollowedArtistsUseCase
import ru.itis.morefy.statistics.domain.models.OverallListeningStats
import ru.itis.morefy.statistics.domain.service.UserStatsService
import ru.itis.morefy.statistics.domain.usecase.GetUserOverallListeningStatsUseCase
import ru.itis.morefy.statistics.domain.usecase.GetUserTopArtistsUseCase
import ru.itis.morefy.statistics.domain.usecase.GetUserTopTracksUseCase
import javax.inject.Inject

class UserStatsServiceImpl @Inject constructor(
    private val getUserTopArtistsUseCase: GetUserTopArtistsUseCase,
    private val getArtistUseCase: GetArtistUseCase,
    private val getUserTopTracksUseCase: GetUserTopTracksUseCase,
    private val getUserOverallListeningStatsUseCase: GetUserOverallListeningStatsUseCase,
): UserStatsService {

    override suspend fun getCurrentUserTopGenresByTopArtists(timeRange: String): Map<Genre, Int> {
        val artists = getUserTopArtistsUseCase(timeRange, MAX_LIMIT_AMOUNT)
        val genresMap: MutableMap<Genre, Int> = HashMap()
        val totalCount = 0

        for (artist: Artist in artists) {
            var currentGenres: List<Genre> = ArrayList()

            if (artist.genres == null) {
                val art = getArtistUseCase(artist.id)
                art.genres?.let {
                    currentGenres = it
                }
            } else {
                currentGenres = artist.genres
            }

            for (genre: Genre in currentGenres) {
                if (genresMap.containsKey(genre))
                    genresMap.replace(genre, ++genresMap.get(genre))
            }

        }
    }

    override fun getUserOverallListeningStatsUseCase(timeRange: String): OverallListeningStats {
        TODO("Not yet implemented")
    }
}
