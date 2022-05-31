package ru.itis.morefy.core.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.itis.morefy.core.data.response.player.PlayerResponse

interface SpotifyPlayerApi {

    // https://developer.spotify.com/documentation/web-api/reference/#/operations/get-information-about-the-users-current-playback

    @GET("me/player/recently-played")
    suspend fun getRecentlyPlayedTracks(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): PlayerResponse

    @GET("me/player/recently-played")
    suspend fun getRecentlyPlayedTracksAfter(
        @Query("limit") limit: Int,
        @Query("after") after: Long,
    ): PlayerResponse

    @GET("me/player/recently-played")
    suspend fun getRecentlyPlayedTracksBefore(
        @Query("limit") limit: Int,
        @Query("before") before: Long,
    ): PlayerResponse
}
