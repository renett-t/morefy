package ru.itis.morefy.core.data.response.player

data class PlayerResponse(
    val cursors: Cursors,
    val href: String,
    val items: List<Item>,
    val limit: Int,
    val next: String,
    val total: Int
)