package ru.itis.morefy.core.data.response.common

data class Tracks(
    val href: String,
    val items: List<TrackItem>,
    val total: Int,
    val limit: Int,
    val offset: Int,
    val previous: String?,
    val next: String?,
)
