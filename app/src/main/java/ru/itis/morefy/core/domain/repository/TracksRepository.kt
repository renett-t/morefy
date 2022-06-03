package ru.itis.morefy.core.domain.repository

import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.core.domain.models.features.TrackFeatures

interface TracksRepository {
    suspend fun getTrackById(id: String): Track
    suspend fun getTracksByIds(ids: List<String>): List<Track>
    suspend fun getTrackFeatures(id: String): TrackFeatures
    suspend fun getTracksFeatures(ids: List<String>): List<TrackFeatures>
}
