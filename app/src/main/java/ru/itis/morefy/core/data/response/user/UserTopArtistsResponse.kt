package ru.itis.morefy.core.data.response.user

import ru.itis.morefy.core.data.response.common.ArtistResponse

data class UserTopArtistsResponse(
    val href: String,
    val items: List<ArtistResponse>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int
)
