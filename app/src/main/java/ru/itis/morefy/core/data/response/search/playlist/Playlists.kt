package ru.itis.morefy.core.data.response.search.playlist

import ru.itis.morefy.core.data.response.common.PlaylistItemShorted

data class Playlists(
    val href: String,
    val items: List<PlaylistItemShorted>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int
)
