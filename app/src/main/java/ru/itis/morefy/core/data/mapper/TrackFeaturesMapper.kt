package ru.itis.morefy.core.data.mapper

import ru.itis.morefy.core.data.response.track.TrackAudioFeatures
import ru.itis.morefy.core.data.response.track.TracksAudioFeatures
import ru.itis.morefy.core.domain.models.features.TrackFeatures
import javax.inject.Inject

class TrackFeaturesMapper @Inject constructor() {

    fun mapFrom(response: TrackAudioFeatures): TrackFeatures {
        return TrackFeatures(
            response.id, response.acousticness,
            response.danceability, response.energy,
            response.instrumentalness, response.liveness,
            response.loudness, response.speechiness,
            response.valence,
            response.mode, response.key, // todo: add mapping to string representation
            response.tempo, response.time_signature,
            response.analysis_url
        )
    }

    fun mapFrom(response: TracksAudioFeatures): List<TrackFeatures> {
        val list = ArrayList<TrackFeatures>()
        for (item in response.audio_features) {
            list.add(
                mapFrom(item)
            )
        }
        return list
    }
}
