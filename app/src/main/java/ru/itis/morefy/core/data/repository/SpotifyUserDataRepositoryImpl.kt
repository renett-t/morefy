package ru.itis.morefy.core.data.repository

import android.util.Log
import retrofit2.HttpException
import ru.itis.morefy.core.data.api.MAX_LIMIT_AMOUNT
import ru.itis.morefy.core.data.api.SpotifyPlayerApi
import ru.itis.morefy.core.data.api.SpotifyPlaylistsApi
import ru.itis.morefy.core.data.api.SpotifyUsersApi
import ru.itis.morefy.core.data.mapper.ArtistsMapper
import ru.itis.morefy.core.data.mapper.PlaylistsMapper
import ru.itis.morefy.core.data.mapper.TracksMapper
import ru.itis.morefy.core.data.mapper.UserDataMapper
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Playlist
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.core.domain.models.User
import ru.itis.morefy.core.domain.repository.UserDataRepository
import javax.inject.Inject

class SpotifyUserDataRepositoryImpl @Inject constructor(
    private val usersApi: SpotifyUsersApi,
    private val playlistsApi: SpotifyPlaylistsApi,
    private val playerApi: SpotifyPlayerApi,
    private val tracksMapper: TracksMapper,
    private val artistsMapper: ArtistsMapper,
    private val userMapper: UserDataMapper,
    private val playlistsMapper: PlaylistsMapper,
) : UserDataRepository {

    override suspend fun getCurrentUserTopTracks(timeRange: String, amount: Int): List<Track> {
        try {
            if (amount > MAX_LIMIT_AMOUNT) {
                val response = usersApi.getUserTopTracks(timeRange, MAX_LIMIT_AMOUNT, 0)
                var amountLeft = amount - MAX_LIMIT_AMOUNT
                return if (amountLeft > 0) {
                    val list = tracksMapper.mapFrom(response).toMutableList()
                    var offset = MAX_LIMIT_AMOUNT
                    while (amountLeft > 0) {
                        val amountToRequest = Math.min(MAX_LIMIT_AMOUNT, amountLeft)
                        val resp = usersApi.getUserTopTracks(timeRange, amountToRequest, offset)
                        list.addAll(tracksMapper.mapFrom(resp))
                        offset += amountToRequest
                        amountLeft -= amountToRequest
                    }
                    list
                } else {
                    tracksMapper.mapFrom(response)
                }
            } else {
                val response = usersApi.getUserTopTracks(timeRange, amount, 0)
                return tracksMapper.mapFrom(response)
            }
        } catch (e: HttpException) {
            Log.e("UserDataRepo", "Get Top Tracks Exception: ${e.message()}")
            throw e
        }
    }

    override suspend fun getCurrentUserTopArtists(timeRange: String, amount: Int): List<Artist> {
        try {
            if (amount > MAX_LIMIT_AMOUNT) {
                val response = usersApi.getUserTopArtists(timeRange, MAX_LIMIT_AMOUNT, 0)
                var amountLeft = amount - MAX_LIMIT_AMOUNT
                return if (amountLeft > 0) {
                    val list = artistsMapper.mapFrom(response).toMutableList()
                    var offset = MAX_LIMIT_AMOUNT
                    while (amountLeft > 0) {
                        val amountToRequest = Math.min(MAX_LIMIT_AMOUNT, amountLeft)
                        val resp = usersApi.getUserTopArtists(timeRange, amountToRequest, offset)
                        list.addAll(artistsMapper.mapFrom(resp))
                        offset += amountToRequest
                        amountLeft -= amountToRequest
                    }
                    list
                } else {
                    artistsMapper.mapFrom(response)
                }
            } else {
                val response = usersApi.getUserTopArtists(timeRange, amount, 0)
                return artistsMapper.mapFrom(response)
            }
        } catch (e: HttpException) {
            Log.e("UserDataRepo", "Get Top Artists Exception: ${e.message()}")
            throw e
        }
    }

    override suspend fun getCurrentUserPlaylists(): List<Playlist> {
        try {
            val response = playlistsApi.getCurrentUserPlaylists(MAX_LIMIT_AMOUNT, 0)
            val amount = response.total

            return if (amount > MAX_LIMIT_AMOUNT) {
                val list = playlistsMapper.mapFrom(response).toMutableList()
                var amountLeft = amount - MAX_LIMIT_AMOUNT
                var offset = MAX_LIMIT_AMOUNT
                while (amountLeft > 0) {
                    val amountToRequest = Math.min(MAX_LIMIT_AMOUNT, amountLeft)
                    val resp = playlistsApi.getCurrentUserPlaylists(amountToRequest, offset)
                    offset += amountToRequest
                    list.addAll(playlistsMapper.mapFrom(resp))
                    amountLeft -= amountToRequest
                }
                list
            } else {
                playlistsMapper.mapFrom(response)
            }
        } catch (ex: HttpException) {
            Log.e("UserDataRepo", "Playlists Exception: ${ex.message()}")
            throw ex
        }
    }

    override suspend fun getCurrentUserFollowedArtists(): List<Artist> {
        try {
            val response = usersApi.getUserFollowedArtists(MAX_LIMIT_AMOUNT)
            val amount = response.artists.total

            return if (amount > MAX_LIMIT_AMOUNT) {
                val list = artistsMapper.mapFrom(response).toMutableList()
                var after = response.artists.cursors.after
                while (after != null) {
                    val resp = usersApi.getUserFollowedArtists(after, MAX_LIMIT_AMOUNT)
                    after = resp.artists.cursors.after
                    list.addAll(artistsMapper.mapFrom(resp))
                }
                list
            } else {
                artistsMapper.mapFrom(response)
            }

        } catch (ex: HttpException) {
            Log.e("UserDataRepo", "Followed Artists Exception: ${ex.message()}")
            throw ex
        }
    }

    override suspend fun getCurrentUserFollowedArtistsCount(): Int {
        try {
            return usersApi.getUserFollowedArtists(1)
                .artists.total
        } catch (ex: HttpException) {
            Log.e("UserDataRepo", "Followed Artists Count Exception: ${ex.message()}")
            throw ex
        }
    }

    override suspend fun getCurrentUserProfile(): User {
        return try {
            val userResponse = usersApi.getUserProfile()
            userMapper.mapFrom(userResponse)
        } catch (e: HttpException) {
            Log.e("UserDataRepo", "Current User Profile Exception: ${e.message()}")
            throw e
        }
    }

    override suspend fun getFeaturedPlaylists(): List<Playlist> {
        try {
            val response = playlistsApi
                .getFeaturedPlaylists( MAX_LIMIT_AMOUNT, "ru_RU",0)
            val amount = response.playlists.total

            return if (amount > MAX_LIMIT_AMOUNT) {
                val list = playlistsMapper.mapFrom(response.playlists).toMutableList()
                var amountLeft = amount - MAX_LIMIT_AMOUNT
                var offset = MAX_LIMIT_AMOUNT
                while (amountLeft > 0) {
                    val amountToRequest = Math.min(MAX_LIMIT_AMOUNT, amountLeft)
                    val resp = playlistsApi.getCurrentUserPlaylists(amountToRequest, offset)
                    offset += amountToRequest
                    list.addAll(playlistsMapper.mapFrom(resp))
                    amountLeft -= amountToRequest
                }
                list
            } else {
                playlistsMapper.mapFrom(response.playlists)
            }
        } catch (ex: HttpException) {
            ex.printStackTrace()
            Log.e("UserDataRepo", "Playlists Exception: ${ex.message()}")
            throw ex
        }
    }

    override suspend fun getRecentlyPlayedTracks():List<Track> {
        try {
            // if amount > 50 =) => need to create multiple requests
            Log.e("USER DATA REPO", "SENDING REQUEST")
            val tracksResponse = playerApi.getRecentlyPlayedTracks(1,0)
            return tracksMapper.mapFrom(tracksResponse)
        } catch (ex: HttpException) {
            ex.printStackTrace()
            Log.e("UserDataRepo", "Track Exception: ${ex.message()}")
            throw ex
        }
    }
}
