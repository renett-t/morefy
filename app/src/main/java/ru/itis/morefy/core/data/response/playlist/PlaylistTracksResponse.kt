package ru.itis.morefy.core.data.response.playlist

data class PlaylistTracksResponse(
    val href: String,
    val items: List<PlaylistItem>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int
)
