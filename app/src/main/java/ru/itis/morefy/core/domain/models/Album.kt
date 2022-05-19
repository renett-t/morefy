package ru.itis.morefy.core.domain.models

import ru.itis.morefy.core.domain.models.features.AverageTracksFeatures
import java.util.Date

data class Album (
    val id: String,

    val type: String,
    val tracksCount: Int,

    val name: String,
    val imageUrl: String,

    val artists: List<Artist>?,
    val tracks: List<Track>?,

    val releaseDate: Date,
    val restrictions: String?,

    val statistics: AverageTracksFeatures?,

    val uri: String,
)
