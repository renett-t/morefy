package ru.itis.morefy.core.data.response.search.artist

import ru.itis.morefy.core.data.response.common.ArtistResponse

data class Artists(
    val href: String,
    val items: List<ArtistResponse>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int
)
