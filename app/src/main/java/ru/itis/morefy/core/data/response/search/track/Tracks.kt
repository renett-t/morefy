package ru.itis.morefy.core.data.response.search.track

import ru.itis.morefy.core.data.response.common.Track

data class Tracks(
    val href: String,
    val items: List<Track>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int
)
