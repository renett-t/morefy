package ru.itis.morefy.core.data.response.user

import ru.itis.morefy.core.data.response.common.ArtistResponse

data class Artists(
    val cursors: Cursors,
    val href: String,
    val items: List<ArtistResponse>,
    val limit: Int,
    val next: String?,
    val total: Int,
)
