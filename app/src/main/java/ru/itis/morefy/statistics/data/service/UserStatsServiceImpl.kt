package ru.itis.morefy.statistics.data.service

import android.util.Log
import ru.itis.morefy.core.data.api.MAX_LIMIT_AMOUNT
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Genre
import ru.itis.morefy.core.domain.models.features.AverageTracksFeatures
import ru.itis.morefy.core.domain.models.features.MusicalKey
import ru.itis.morefy.core.domain.usecase.GetArtistUseCase
import ru.itis.morefy.core.domain.usecase.GetTrackUseCase
import ru.itis.morefy.statistics.domain.service.UserStatsService
import ru.itis.morefy.statistics.domain.usecase.GetUserTopArtistsUseCase
import ru.itis.morefy.statistics.domain.usecase.GetUserTopTracksUseCase
import java.util.stream.Collectors
import javax.inject.Inject

class UserStatsServiceImpl @Inject constructor(
    private val getUserTopArtistsUseCase: GetUserTopArtistsUseCase,
    private val getArtistUseCase: GetArtistUseCase,
    private val getUserTopTracksUseCase: GetUserTopTracksUseCase,
    private val getTrackUseCase: GetTrackUseCase
) : UserStatsService {

    override suspend fun getCurrentUserTopGenresByTopArtists(timeRange: String): Map<Genre, Int> {
        val artists = getUserTopArtistsUseCase(timeRange, MAX_LIMIT_AMOUNT)
        val genresMap: MutableMap<Genre, Int> = HashMap()
        var totalCount = 0

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
                totalCount++
                if (genresMap.containsKey(genre))
                    genresMap.replace(genre, genresMap[genre]?.plus(1) ?: 1)
                else
                    genresMap[genre] = 1
            }

        }

        genresMap.mapValues { entry ->
            entry.value / totalCount
        }

        saveTop5GenresToDb(genresMap, timeRange)
        return genresMap
    }

    override suspend fun getUserOverallListeningStats(timeRange: String): AverageTracksFeatures {
        val topTracks = getUserTopTracksUseCase(timeRange, MAX_LIMIT_AMOUNT)
        val features = getTrackUseCase.getTracksFeatures(
            topTracks.stream().map { it.id }.collect(Collectors.toList())
        )
        val modesMap: MutableMap<Int, Int> = HashMap()
        val keysMap: MutableMap<MusicalKey, Int> = HashMap()

        val average = AverageTracksFeatures()

        var count = 0
        for (item in features) {
            count++
            average.acousticness += item.acousticness
            average.danceability += item.danceability
            average.energy += item.energy
            average.instrumentalness += item.instrumentalness
            average.liveness += item.liveness
            average.loudness += item.loudness
            average.speechiness += item.speechiness
            average.valence += item.valence
            average.tempo += item.tempo

            if (modesMap.containsKey(item.mode))
                modesMap.replace(item.mode, modesMap[item.mode]?.plus(1) ?: 1)
            else
                modesMap[item.mode] = 1

            if (keysMap.containsKey(item.key))
                keysMap.replace(item.key, keysMap[item.key]?.plus(1) ?: 1)
            else
                keysMap[item.key] = 1

        }

        average.mode = getTheMostFrequentItem(modesMap)
        average.key = getTheMostFrequentItem(keysMap)
        average.acousticness = average.acousticness / count
        average.danceability = average.danceability / count
        average.energy = average.energy / count
        average.instrumentalness = average.instrumentalness / count
        average.liveness = average.liveness / count
        average.loudness = average.loudness / count
        average.speechiness = average.speechiness / count
        average.valence = average.valence / count
        average.tempo = average.tempo / count

        saveCalculatedAverage(average, timeRange)
        return average
    }

    private fun <T> getTheMostFrequentItem(map: MutableMap<T, Int>): T {
        var max = -1
        var key: T = map.keys.first()

        for (entry in map.entries) {
            if (entry.value > max) {
                max = entry.value
                key = entry.key
            }
        }

        return key
    }

    private fun saveTop5GenresToDb(genresMap: MutableMap<Genre, Int>, timeRange: String) {
        // todo save to db
    }

    private fun saveCalculatedAverage(average: AverageTracksFeatures, timeRange: String) {
        // todo save to db
    }
}
