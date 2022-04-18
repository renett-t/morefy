package ru.itis.morefy.core.domain.models

import ru.itis.morefy.core.domain.models.features.AverageTracksFeatures
import java.time.LocalDate

data class Album (
    private val id: String,

    private val type: String,
    private val tracksCount: Int,

    private val name: String,
    private val imageUrl: String,

    private val artists: List<Artist>,
    private val tracks: List<Track>,

    private val releaseDate: LocalDate,
    private val restrictions: String,

    private val statistics: AverageTracksFeatures?,

    private val uri: String,
)
