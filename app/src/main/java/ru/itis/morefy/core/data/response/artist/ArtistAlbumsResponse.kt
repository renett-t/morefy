package ru.itis.morefy.core.data.response.artist

import ru.itis.morefy.core.data.response.common.AlbumItem

data class ArtistAlbumsResponse(
    val href: String,
    val items: List<AlbumItem>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int
)
