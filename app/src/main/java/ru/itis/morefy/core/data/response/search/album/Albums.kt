package ru.itis.morefy.core.data.response.search.album

import ru.itis.morefy.core.data.response.common.AlbumItem

data class Albums(
    val href: String,
    val items: List<AlbumItem>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int
)
