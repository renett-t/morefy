package ru.itis.morefy.core.data.mapper

import ru.itis.morefy.core.data.response.track.TrackAudioFeatures
import ru.itis.morefy.core.data.response.track.TracksAudioFeatures
import ru.itis.morefy.core.domain.models.features.MusicalKey
import ru.itis.morefy.core.domain.models.features.MusicalMode
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
            getMusicalMode(response.mode), getMusicalKey(response.key),
            response.tempo, response.time_signature,
            response.analysis_url
        )
    }

    private fun getMusicalMode(mode: Int): MusicalMode {
        return when(mode) {
            0 -> MusicalMode.MINOR
            1 -> MusicalMode.MAJOR
            else -> MusicalMode.UNDEFINED
        }
    }

    private fun getMusicalKey(key: Int): MusicalKey {
        return when(key) {
            -1 -> MusicalKey.UNDEFINED
            0 -> MusicalKey.C
            1 -> MusicalKey.CD
            2 -> MusicalKey.D
            3 -> MusicalKey.DE
            4 -> MusicalKey.E
            5 -> MusicalKey.F
            6 -> MusicalKey.FG
            7 -> MusicalKey.G
            8 -> MusicalKey.GA
            9 -> MusicalKey.A
            10 -> MusicalKey.AB
            11 -> MusicalKey.B
            else -> MusicalKey.UNDEFINED
        }
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
