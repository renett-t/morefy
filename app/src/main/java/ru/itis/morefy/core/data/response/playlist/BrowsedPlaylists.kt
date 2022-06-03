package ru.itis.morefy.core.data.response.playlist

import ru.itis.morefy.core.data.response.common.PlaylistItemShorted

data class BrowsedPlaylists(
    val href: String,
    val items: List<PlaylistItemShorted>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int
)
