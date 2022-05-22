package ru.itis.morefy.core.data.repository

import android.util.Log
import retrofit2.HttpException
import ru.itis.morefy.core.data.api.MAX_LIMIT_AMOUNT
import ru.itis.morefy.core.data.api.SpotifyPlaylistsApi
import ru.itis.morefy.core.data.api.SpotifyUsersApi
import ru.itis.morefy.core.data.mapper.ArtistsMapper
import ru.itis.morefy.core.data.mapper.TracksMapper
import ru.itis.morefy.core.data.mapper.UserDataMapper
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Playlist
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.core.domain.models.User
import ru.itis.morefy.core.domain.repository.UserDataRepository
import java.lang.Math.min
import javax.inject.Inject

class SpotifyUserDataRepositoryImpl @Inject constructor(
    private val usersApi: SpotifyUsersApi,
    private val playlistsApi: SpotifyPlaylistsApi,
    private val tracksMapper: TracksMapper,
    private val artistsMapper: ArtistsMapper,
    private val userMapper: UserDataMapper,
) : UserDataRepository {

    override suspend fun getCurrentUserTopTracks(timeRange: String, amount: Int): List<Track> {
        try {
            if (amount > MAX_LIMIT_AMOUNT) {
                val response = usersApi.getUserTopTracks(timeRange, MAX_LIMIT_AMOUNT, 0)
                val list = tracksMapper.mapFrom(response).toMutableList()
                return if (response.total > MAX_LIMIT_AMOUNT) {
                    var amountLeft = amount - MAX_LIMIT_AMOUNT
                    while (amountLeft > 0) {
                        val amountToRequest = min(MAX_LIMIT_AMOUNT, amountLeft)
                        val resp = usersApi.getUserTopTracks(timeRange, amountToRequest, response.items.size)
                        list.addAll(tracksMapper.mapFrom(resp))
                        amountLeft -= MAX_LIMIT_AMOUNT
                    }
                    list
                } else
                    list
            } else {
                val tracksResponse = usersApi.getUserTopTracks(timeRange, amount, 0)
                return tracksMapper.mapFrom(tracksResponse)
            }
        } catch (e: HttpException) {
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
        return try {
            val userResponse = usersApi.getUserProfile()
            userMapper.mapFrom(userResponse)
        } catch (e: HttpException) {
            throw e
        }
    }
}
