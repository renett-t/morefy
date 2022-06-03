package ru.itis.morefy.core.domain.models.features

data class TrackFeatures (
    val trackId: String,

    val acousticness: Float,
    val danceability: Float,
    val energy: Float,
    val instrumentalness: Float,
    val liveness: Float,
    val loudness: Float,
    val speechiness: Float,
    val valence: Float,

    val mode: MusicalMode,
    val key: MusicalKey,

    val tempo: Float,
    val timeSignature: Int,

    val audioAnalysisUrl: String,
)
