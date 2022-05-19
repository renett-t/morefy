package ru.itis.morefy.core.data.repository

import android.util.Log
import retrofit2.HttpException
import ru.itis.morefy.core.data.api.SpotifyPlaylistsApi
import ru.itis.morefy.core.data.api.SpotifyUsersApi
import ru.itis.morefy.core.data.mapper.ArtistsMapper
import ru.itis.morefy.core.data.mapper.SpotifyEntitiesMapper
import ru.itis.morefy.core.data.mapper.TracksMapper
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Playlist
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.core.domain.models.User
import ru.itis.morefy.core.domain.repository.UserDataRepository
import javax.inject.Inject

class SpotifyUserDataRepositoryImpl @Inject constructor(
    private val usersApi: SpotifyUsersApi,
    private val tracksMapper: TracksMapper,
    private val artistsMapper: ArtistsMapper
) : UserDataRepository {

    override suspend fun getCurrentUserTopTracks(timeRange: String, amount: Int): List<Track> {
        try {
            // if amount > 50 =) => need to create multiple requests
            Log.e("TOP TRACKS REPO", "SENDING REQUEST")
            val tracksResponse = usersApi.getUserTopTracks(timeRange, amount, 0)
            return tracksMapper.mapFrom(tracksResponse)
        } catch (e: HttpException) {
            Log.e("USER DATA REPO IMPL", e.message())
            throw e
        }
    }

    override suspend fun getCurrentUserTopArtists(timeRange: String, amount: Int): List<Artist> {
        try {
            // if amount > 50 =) => need to create multiple requests
            val artistsResponse = usersApi.getUserTopArtists(timeRange, amount, 0)
            return artistsMapper.mapFrom(artistsResponse)
        } catch (e: HttpException) {
            Log.e("USER DATA REPO IMPL", e.message())
            throw e
        }
    }

    override suspend fun getCurrentUserFollowedPlaylists(): List<Playlist> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUserFollowedArtists(): List<Artist> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUserProfile(): User {
        TODO("Not yet implemented")
    }
}
