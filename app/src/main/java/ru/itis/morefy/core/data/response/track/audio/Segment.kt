package ru.itis.morefy.core.data.response.track.audio

data class Segment(
    val confidence: Double,
    val duration: Double,
    val loudness_end: Double,
    val loudness_max: Double,
    val loudness_max_time: Double,
    val loudness_start: Double,
    val pitches: List<Double>,
    val start: Double,
    val timbre: List<Double>
)
