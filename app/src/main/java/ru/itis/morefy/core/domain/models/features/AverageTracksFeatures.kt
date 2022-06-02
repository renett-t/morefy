package ru.itis.morefy.core.domain.models.features

data class AverageTracksFeatures (
    val source: Set<TrackFeatures>?,

    val acousticness: Float,
    val danceability: Float,
    val energy: Float,
    val instrumentalness: Float,
    val liveness: Float,
    val loudness: Float,
    val speechiness: Float,
    val valence: Float,

    val mode: Int,
    val key: MusicalKey,

    val tempo: Float,
    val timeSignature: Int,
)
