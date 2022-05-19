package ru.itis.morefy.core.domain.models.features

data class TrackFeatures (
    private val trackId: String,

    private val acousticness: Float,
    private val danceability: Float,
    private val energy: Float,
    private val instrumentalness: Float,
    private val liveness: Float,
    private val loudness: Float,
    private val speechiness: Float,
    private val valence: Float,

    private val mode: Int,
    private val key: Int,

    private val tempo: Float,
    private val timeSignature: Int,

    private val audioAnalysisUrl: String,
)
