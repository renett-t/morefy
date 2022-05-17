package ru.itis.morefy.core.data.response.artist

import ru.itis.morefy.core.data.response.common.Track

data class ArtistTopTracksResponse(
    val tracks: List<Track>
)
