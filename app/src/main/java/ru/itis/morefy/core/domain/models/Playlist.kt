package ru.itis.morefy.core.domain.models

import ru.itis.morefy.core.domain.models.features.AverageTracksFeatures

data class Playlist (
    private val id: String,

    private val type: String,
    private val tracksCount: Int,

    private val name: String,
    private val imageUrl: String,
    private val isCollaborative: Boolean,
    private val isBoolean: Boolean,
    private val followerCount: Int,
    private val owners: List<SpotifyUser>,
    private val tracks: List<Track>,

    private val statistics: AverageTracksFeatures?,

    private val uri: String,
)
