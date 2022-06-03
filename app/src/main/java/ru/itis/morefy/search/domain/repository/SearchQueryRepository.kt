package ru.itis.morefy.search.domain.repository

import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Track

interface SearchQueryRepository {
    suspend fun getArtistsByQuery(query: String, amount: Int): List<Artist>
    suspend fun getTracksByQuery(query: String, amount: Int): List<Track>
}
