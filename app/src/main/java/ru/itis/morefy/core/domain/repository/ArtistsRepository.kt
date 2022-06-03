package ru.itis.morefy.core.domain.repository

import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Track

interface ArtistsRepository {
    suspend fun getArtistById(id: String): Artist
    suspend fun getArtistsByIds(ids: List<String>): List<Artist>
    suspend fun getSimilarArtists(id: String): List<Artist>
    suspend fun getArtistPopularTracks(id: String): List<Track>
}
