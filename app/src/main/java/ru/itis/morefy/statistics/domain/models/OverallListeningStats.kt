package ru.itis.morefy.statistics.domain.models

import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Genre
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.core.domain.models.features.AverageTracksFeatures

data class OverallListeningStats(
    val topTracks: List<Track>,
    val topArtists: List<Artist>,
    val topGenres: List<Genre>,
    val averageTracksFeatures: AverageTracksFeatures
)
