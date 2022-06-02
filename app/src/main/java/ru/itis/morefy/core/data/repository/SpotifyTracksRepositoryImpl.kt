package ru.itis.morefy.core.data.repository

import android.util.Log
import retrofit2.HttpException
import ru.itis.morefy.core.data.api.DEFAULT_MARKET
import ru.itis.morefy.core.data.api.MAX_LIMIT_AMOUNT
import ru.itis.morefy.core.data.api.SpotifyTracksApi
import ru.itis.morefy.core.data.mapper.TrackFeaturesMapper
import ru.itis.morefy.core.data.mapper.TracksMapper
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.core.domain.models.features.TrackFeatures
import ru.itis.morefy.core.domain.repository.TracksRepository
import javax.inject.Inject

class SpotifyTracksRepositoryImpl @Inject constructor(
    private val tracksApi: SpotifyTracksApi,
    private val tracksMapper: TracksMapper,
    private val trackFeaturesMapper: TrackFeaturesMapper
) : TracksRepository {

    override suspend fun getTrackById(id: String): Track {
        try {
            return tracksMapper.mapFrom(tracksApi.getTrackById(id, DEFAULT_MARKET))
        } catch (e: HttpException) {
            Log.e("TracksRepository", "Get Track By Id Exception: ${e.message()}")
            throw e
        }
    }

    override suspend fun getTracksByIds(ids: List<String>): List<Track> {
        try {
            if (ids.size > MAX_LIMIT_AMOUNT) {
                (TODO())
            } else {
                return tracksMapper.mapFrom(tracksApi.getTracksByIds(getIdsParameter(ids), DEFAULT_MARKET))
            }
        } catch (e: HttpException) {
            Log.e("TracksRepository", "Get Tracks By Ids Exception: ${e.message()}")
            throw e
        }
    }

    override suspend fun getTrackFeatures(id: String): TrackFeatures {
        try {
            return trackFeaturesMapper.mapFrom(tracksApi.getTrackAudioFeaturesById(id))
        } catch (e: HttpException) {
            Log.e("TracksRepository", "Get Track Features By Track Id Exception: ${e.message()}")
            throw e
        }
    }

    override suspend fun getTracksFeatures(ids: List<String>): List<TrackFeatures> {
        try {
            if (ids.size > MAX_LIMIT_AMOUNT) {
                (TODO())
            } else {
                return trackFeaturesMapper.mapFrom(tracksApi.getTracksAudioFeaturesByIds(getIdsParameter(ids)))
            }
        } catch (e: HttpException) {
            Log.e("TracksRepository", "Get Tracks Features By Tracks Ids Exception: ${e.message()}")
            throw e
        }
    }

    private fun getIdsParameter(ids: List<String>): String {
        return ids.joinToString(separator = ",")
    }
}
