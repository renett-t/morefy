package ru.itis.morefy.core.data.response.player

import ru.itis.morefy.core.data.response.common.Track

data class PlayedTrack(
    val track: Track,
    val played_at: String
)
