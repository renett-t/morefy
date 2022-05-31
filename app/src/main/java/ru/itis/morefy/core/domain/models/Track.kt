package ru.itis.morefy.core.domain.models

import ru.itis.morefy.core.domain.models.features.TrackFeatures

data class Track (
    val id: String,

    val album: Album,
    val artists: List<Artist>,

    val durationMs: Int,
    val isExplicit: Boolean,

    val name: String,

    val popularity: Int,
    val statistics: TrackFeatures?,

    val previewUrl: String?,
    val uri: String,
)
