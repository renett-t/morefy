package ru.itis.morefy.core.data.response.artist

import ru.itis.morefy.core.data.response.common.ArtistResponse

data class RelatedArtistsResponse(
    val artists: List<ArtistResponse>
)
