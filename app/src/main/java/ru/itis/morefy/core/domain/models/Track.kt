package ru.itis.morefy.core.domain.models

import ru.itis.morefy.core.domain.models.features.TrackFeatures


data class Track (
    private val id: String,

    private val album: Album,
    private val artists: List<Artist>,

    private val durationMs: Int,
    private val isExplicit: Boolean,

    private val name: String,

    private val popularity: Int,
    private val statistics: TrackFeatures?,

    private val previewUrl: String,
    private val uri: String,
)
