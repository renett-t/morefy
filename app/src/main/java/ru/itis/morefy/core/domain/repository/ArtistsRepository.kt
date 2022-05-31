package ru.itis.morefy.core.domain.repository

import ru.itis.morefy.core.domain.models.Artist

interface ArtistsRepository {
    suspend fun getArtistById(id: String): Artist
    suspend fun getArtistsByIds(ids: List<String>): List<Artist>
}
