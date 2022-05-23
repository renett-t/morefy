package ru.itis.morefy.core.domain.repository

import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Playlist
import ru.itis.morefy.core.domain.models.User
import ru.itis.morefy.core.domain.models.Track

interface UserDataRepository {
    suspend fun getCurrentUserTopTracks(timeRange: String, amount: Int): List<Track>
    suspend fun getCurrentUserTopArtists(timeRange: String, amount: Int): List<Artist>
    suspend fun getCurrentUserPlaylists(): List<Playlist>
    suspend fun getCurrentUserFollowedArtists(): List<Artist>
    suspend fun getCurrentUserFollowedArtistsCount(): Int
    suspend fun getCurrentUserProfile(): User
}
