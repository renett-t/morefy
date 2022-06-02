package ru.itis.morefy.statistics.data.service

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.morefy.core.data.api.MAX_LIMIT_AMOUNT
import ru.itis.morefy.core.data.database.dao.AverageStatsDao
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Genre
import ru.itis.morefy.core.domain.models.TimeRange
import ru.itis.morefy.core.domain.models.datamodels.AverageStats
import ru.itis.morefy.core.domain.models.features.AverageTracksFeatures
import ru.itis.morefy.core.domain.models.features.MusicalKey
import ru.itis.morefy.core.domain.models.features.MusicalMode
import ru.itis.morefy.core.domain.usecase.GetArtistUseCase
import ru.itis.morefy.core.domain.usecase.GetTrackUseCase
import ru.itis.morefy.statistics.domain.service.UserStatsService
import ru.itis.morefy.statistics.domain.usecase.GetUserTopArtistsUseCase
import ru.itis.morefy.statistics.domain.usecase.GetUserTopTracksUseCase
import java.util.*
import java.util.stream.Collectors
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

private const val TIME_PERIOD_MS = 24 * 60 * 10000 // 24 hours
class UserStatsServiceImpl @Inject constructor(
    private val getUserTopArtistsUseCase: GetUserTopArtistsUseCase,
    private val getArtistUseCase: GetArtistUseCase,
    private val getUserTopTracksUseCase: GetUserTopTracksUseCase,
    private val getTrackUseCase: GetTrackUseCase,
    private val avgStatsDao: AverageStatsDao
) : UserStatsService {

    override suspend fun getCurrentUserTopGenresByTopArtists(timeRange: TimeRange): Map<Genre, Int> {
        val artists = getUserTopArtistsUseCase(timeRange.time, MAX_LIMIT_AMOUNT)
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

        saveTop5GenresToDb(genresMap, timeRange.time)
        return genresMap
    }

    override suspend fun getUserOverallListeningStats(timeRange: TimeRange): AverageTracksFeatures {
        val stats = avgStatsDao.getStatsAfterTimeStampWithRange(
            Date().time - TIME_PERIOD_MS, timeRange
        )

        Log.d("STATS SERVICE", "Query (getStatsAfterTimeStampWithRange) result: count = ${stats.size}, values = $stats")
        Log.d("STATS SERVICE", "Total amount of entities in db = ${avgStatsDao.getAllStats().size}")

        return if (stats.isNotEmpty()) {
            if (checkIfLastOkay(stats.last(), timeRange)) {
                Log.d("STATS SERVICE", "Last entity is okay, meets requirements, returning ${stats.last()}")
                getAverageFromDbEntity(stats.last())
            } else {
                Log.d("STATS SERVICE", "Entity does not fit requirements: Too old or wrong timeRange (requested for $timeRange). Time Of Entity = ${stats.last().createdAt}, TimeRange = ${stats.last().timeRange}")
                downloadNewAverageEntity(timeRange)
            }
        } else {
            Log.d("STATS SERVICE", "Query has returned empty list, just downloading new entity")
            downloadNewAverageEntity(timeRange)
        }
    }

    private suspend fun downloadNewAverageEntity(timeRange: TimeRange): AverageTracksFeatures {
        val topTracks = getUserTopTracksUseCase(timeRange.time, MAX_LIMIT_AMOUNT)
        val features = getTrackUseCase.getTracksFeatures(
            topTracks.stream().map { it.id }.collect(Collectors.toList())
        )
        val modesMap: MutableMap<MusicalMode, Int> = HashMap()
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

        saveCalculatedAverage(average, timeRange.time)
        return average
    }

    private fun checkIfLastOkay(last: AverageStats, timeRange: TimeRange): Boolean {
        val t1 = last.timeRange.time == timeRange.time
        val t2 = last.createdAt.time + TIME_PERIOD_MS > Date().time
        return t1 && t2
    }

    private fun getAverageFromDbEntity(item: AverageStats): AverageTracksFeatures {
        return AverageTracksFeatures(
            null, item.acousticness, item.danceability, item.energy,
            item.instrumentalness, item.liveness, item.loudness,
            item.speechiness, item.valence,
            item.mode, item.key, item.tempo
        )
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

    private suspend fun saveCalculatedAverage(data: AverageTracksFeatures, timeRange: String) {
        withContext(Dispatchers.IO) {
            val entity = AverageStats(
                0, Date(), getTimeRange(timeRange),
                data.acousticness, data.danceability, data.energy,
                data.instrumentalness, data.liveness, data.loudness,
                data.speechiness, data.valence, data.mode,
                data.key, data.tempo
            )

            avgStatsDao.save(entity)
            Log.d("STATS SERVICE", "Saved new entity = $entity")
        }
    }

    private fun getTimeRange(timeRange: String): TimeRange {
        return when (timeRange) {
            "long_term" -> TimeRange.LONG
            "medium_term" -> TimeRange.MEDIUM
            "short_term" -> TimeRange.SHORT
            else -> TimeRange.UNDEFINED
        }
    }
}
