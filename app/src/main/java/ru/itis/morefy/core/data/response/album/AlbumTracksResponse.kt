package ru.itis.morefy.core.data.response.album

import ru.itis.morefy.core.data.response.common.TrackItem

data class AlbumTracksResponse(
    val href: String,
    val items: List<TrackItem>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int
)
