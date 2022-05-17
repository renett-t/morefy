package ru.itis.morefy.core.data.response.track.audio

data class Meta(
    val analysis_time: Double,
    val analyzer_version: String,
    val detailed_status: String,
    val input_process: String,
    val platform: String,
    val status_code: Int,
    val timestamp: Int
)
