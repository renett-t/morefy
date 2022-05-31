package ru.itis.morefy.core.data.response.player

import ru.itis.morefy.core.data.response.common.Track

data class PlayerResponse(
    val cursors: Cursors,
    val href: String,
    val items: List<PlayedTrack>,
    val limit: Int,
    val next: String,
)
