package ru.itis.morefy.core.domain.models.features

data class AverageTracksFeatures(
    val source: Set<TrackFeatures>?,

    var acousticness: Float,
    var danceability: Float,
    var energy: Float,
    var instrumentalness: Float,
    var liveness: Float,
    var loudness: Float,
    var speechiness: Float,
    var valence: Float,

    var mode: MusicalMode,
    var key: MusicalKey,

    var tempo: Float
) {
    constructor() : this(
        null, -1f, -1f,
        -1f, -1f, -1f, -1f,
        -1f, -1f, MusicalMode.UNDEFINED,
        MusicalKey.UNDEFINED, -1f
    )
}
