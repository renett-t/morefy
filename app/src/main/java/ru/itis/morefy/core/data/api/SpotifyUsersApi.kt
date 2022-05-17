package ru.itis.morefy.core.data.api

import retrofit2.http.*
import ru.itis.morefy.core.data.request.IdsDto
import ru.itis.morefy.core.data.request.IsPublicDto
import ru.itis.morefy.core.data.response.common.CheckFollowingResponse
import ru.itis.morefy.core.data.response.user.*

interface SpotifyUsersApi {
    // users - https://developer.spotify.com/documentation/web-api/reference/#/operations/get-current-users-profile

    @GET("me")
    fun getUserProfile(): CurrentUserProfileResponse

    @GET("users/{user_id}")
    fun getUserProfileByUserId(@Path("user_id") userId: String): UserProfileResponse

    @GET("me/top/artists")
    fun getUserTopArtists(@Query("time_range") timeRange: String): UserTopArtistsResponse

    @GET("me/top/artists")
    fun getUserTopArtists(
        @Query("time_range") timeRange: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): UserTopArtistsResponse

    @GET("me/top/tracks")
    fun getUserTopTracks(@Query("time_range") timeRange: String): UserTopTracksResponse

    @GET("me/top/tracks")
    fun getUserTopTracks(
        @Query("time_range") timeRange: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): UserTopTracksResponse

    @PUT("playlists/{playlist_id}/followers")
    fun followPlaylist(@Path("playlist_id") playlistId: String)

    @PUT("playlists/{playlist_id}/followers")
    fun followPlaylist(@Path("playlist_id") playlistId: String, @Body isPublic: IsPublicDto)

    @DELETE("playlists/{playlist_id}/followers")
    fun unfollowPlaylist(@Path("playlist_id") playlistId: String)

    @GET("me/following?type=artist")
    fun getUserFollowedArtists(): UserFollowedArtistsResponse

    @GET("me/following?type=artist")
    fun getUserFollowedArtists(@Query("limit") limit: Int): UserFollowedArtistsResponse

    @GET("me/following?type=artist")
    fun getUserFollowedArtists(@Query("after") afterArtistId: String): UserFollowedArtistsResponse

    @GET("me/following?type=artist")
    fun getUserFollowedArtists(
        @Query("after") afterArtistId: String,
        @Query("limit") limit: Int
    ): UserFollowedArtistsResponse

    @PUT("me/following?type=artist")
    fun followArtist(
        @Body ids: IdsDto
    )

    @PUT("me/following?type=user")
    fun followUser(
        @Body ids: IdsDto
    )

    @DELETE("me/following?type=artist")
    fun unfollowArtist(
        @Body ids: IdsDto
    )

    @DELETE("me/following?type=user")
    fun unfollowUser(
        @Body ids: IdsDto
    )

    @GET("me/following/contains?type=artist")
    fun checkIfUserFollowsArtist(@Query("ids") ids: String): CheckFollowingResponse

    @GET("me/following/contains?type=user")
    fun checkIfUserFollowsUser(@Query("ids") ids: String): CheckFollowingResponse

    @GET("playlists/{playlist_id}/followers/contains ")
    fun checkIfUsersFollowsPlaylist(
        @Path("playlist_id") playlistId: String,
        @Query("ids") userIds: String
    ): CheckFollowingResponse
}
