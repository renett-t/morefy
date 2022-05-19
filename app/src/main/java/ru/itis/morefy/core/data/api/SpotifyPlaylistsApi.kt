package ru.itis.morefy.core.data.api

import retrofit2.http.*
import ru.itis.morefy.core.data.request.*
import ru.itis.morefy.core.data.response.playlist.*

interface SpotifyPlaylistsApi {

    // playlists - https://developer.spotify.com/documentation/web-api/reference/#/operations/get-playlist

    @GET("playlists/{playlist_id}")
    suspend fun getPlaylistById(
        @Path("playlist_id") id: String,
        @Query("market") market: String
    ): PlaylistResponse

    @GET("playlists/{playlist_id}")
    suspend fun getPlaylistById(
        @Path("playlist_id") id: String,
        @Query("market") market: String,
        @Query("fields") fields: String
    ): PlaylistResponse

    @PUT("playlists/{playlist_id}")
    suspend fun updatePlaylistDetailsById(
        @Path("playlist_id") id: String,
        @Body updatePlaylistDetails: PlaylistDetailsDto,
    )

    @GET("playlists/{playlist_id}/tracks")
    suspend fun getPlaylistTracks(
        @Path("playlist_id") id: String,
        @Query("market") market: String
    ): PlaylistTracksResponse

    @GET("playlists/{playlist_id}/tracks")
    suspend fun getPlaylistTracks(
        @Path("playlist_id") id: String,
        @Query("market") market: String,
        @Query("fields") fields: String
    ): PlaylistTracksResponse

    @GET("playlists/{playlist_id}/tracks")
    suspend fun getPlaylistTracks(
        @Path("playlist_id") id: String,
        @Query("market") market: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PlaylistTracksResponse

    @POST("playlists/{playlist_id}/tracks")
    suspend fun addTracksToPlaylist(
        @Path("playlist_id") playlistId: String,
        @Body changes: AddPlaylistTracksDto
    ): PlaylistUpdateResponse

    @PUT("playlists/{playlist_id}/tracks")
    suspend fun updatePlaylistTracks(
        @Path("playlist_id") playlistId: String,
        @Body changes: UpdatePlaylistTracksDto
    ): PlaylistUpdateResponse

    @DELETE("playlists/{playlist_id}/tracks")
    suspend fun deleteTracksFromPlaylist(
        @Path("playlist_id") playlistId: String,
        @Body changes: DeletePlaylistTracksDto
    ): PlaylistUpdateResponse

    @GET("me/playlists")
    suspend fun getCurrentUserPlaylists(): UserPlaylistsResponse

    @GET("me/playlists")
    suspend fun getCurrentUserPlaylistsLimited(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): UserPlaylistsResponse

    @GET("users/{user_id}/playlists")
    suspend fun getUserPlaylists(
        @Path("user_id") userId: String
    ): UserPlaylistsResponse

    @GET("users/{user_id}/playlists")
    suspend fun getUserPlaylistsLimited(
        @Path("user_id") userId: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): UserPlaylistsResponse

    @POST("users/{user_id}/playlists")
    suspend fun createPlaylist(
        @Path("user_id") userId: String,
        @Body createPlaylist: PlaylistDetailsDto
    ): CreatePlaylistResponse

    @GET("playlists/{playlist_id}/images")
    suspend fun getPlaylistCoverImage(@Path("playlist_id") id: String): PlaylistImageResponse

    @GET("browse/featured-playlists")
    suspend fun getFeaturedPlaylistsGlobal(): BrowsePlaylistsResponse

    @GET("browse/featured-playlists")
    suspend fun getFeaturedPlaylistsGlobalLimited(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): BrowsePlaylistsResponse

    @GET("browse/featured-playlists")
    suspend fun getFeaturedPlaylistsForCountry(
        @Query("country") country: String
    ): BrowsePlaylistsResponse


    @GET("browse/featured-playlists")
    suspend fun getFeaturedPlaylistsForCountryLimited(
        @Query("country") country: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): BrowsePlaylistsResponse


    @GET("browse/featured-playlists")
    suspend fun getFeaturedPlaylistsForCountryLocalised(
        @Query("country") country: String,
        @Query("locale") locale: String,
    ): BrowsePlaylistsResponse

    @GET("browse/featured-playlists")
    suspend fun getFeaturedPlaylistsLocalised(
        @Query("locale") locale: String,
    ): BrowsePlaylistsResponse

    @GET("browse/featured-playlists")
    suspend fun getFeaturedPlaylistsForCountryLocalisedLimited(
        @Query("country") country: String,
        @Query("locale") locale: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): BrowsePlaylistsResponse

    @GET("browse/featured-playlists")
    suspend fun getFeaturedPlaylists(
        @Query("country") country: String,
        @Query("limit") limit: Int,
        @Query("locale") locale: String,
        @Query("offset") offset: Int,
        @Query("timestamp") timestamp: String
    ): BrowsePlaylistsResponse

    @GET("browse/categories/{category_id}/playlists")
    suspend fun getPlaylistsByCategory(
        @Path("category_id") userId: String
    ): CategoryPlaylistsResponse

    @GET("browse/categories/{category_id}/playlists")
    suspend fun getPlaylistsByCategoryLimited(
        @Path("category_id") userId: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): CategoryPlaylistsResponse

    @GET("browse/categories/{category_id}/playlists")
    suspend fun getPlaylistsByCategoryForCountry(
        @Path("category_id") userId: String,
        @Query("country") country: String,
    ): CategoryPlaylistsResponse

    @GET("browse/categories/{category_id}/playlists")
    suspend fun getPlaylistsByCategoryForCountryLimited(
        @Path("category_id") userId: String,
        @Query("country") country: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): CategoryPlaylistsResponse
}
