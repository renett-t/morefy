package ru.itis.morefy.core.data.response.recommendations

import ru.itis.morefy.core.data.response.common.Track

data class RecommendationsResponse(
    val seeds: List<Seed>,
    val tracks: List<Track>
)
