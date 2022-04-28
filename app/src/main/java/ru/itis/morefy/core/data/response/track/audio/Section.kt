package ru.itis.morefy.core.data.response.track.audio

data class Section(
    val confidence: Double,
    val duration: Double,
    val key: Int,
    val key_confidence: Double,
    val loudness: Double,
    val mode: Int,
    val mode_confidence: Double,
    val start: Double,
    val tempo: Double,
    val tempo_confidence: Double,
    val time_signature: Int,
    val time_signature_confidence: Double
)
