package ru.itis.morefy.core.data.api

import retrofit2.http.*
import ru.itis.morefy.core.data.request.CreatePlaylistRequestDto
import ru.itis.morefy.core.data.request.DeletePlaylistTracksDto
import ru.itis.morefy.core.data.request.UpdatePlaylistTracksDto
import ru.itis.morefy.core.data.response.*

interface SpotifyPlaylistsApi {

    // playlists - https://developer.spotify.com/documentation/web-api/reference/#/operations/get-playlist

    @GET("playlists/{playlist_id}")
    fun getPlaylistById(@Path("playlist_id") id: String, @Query("market") market: String) : PlaylistResponse

    @PUT("playlists/{playlist_id}")
    fun updatePlaylistDetailsById(@Path("playlist_id") id: String, @Body name: String, @Body public: Boolean, @Body collaborative: Boolean, @Body description: String)

    @GET("playlists/{playlist_id}/tracks")
    fun getPlaylistTracks(@Path("playlist_id") id: String, @Query("market") market: String)

    @GET("playlists/{playlist_id}/tracks")
    fun getPlaylistTracks(@Path("playlist_id") id: String, @Query("market") market: String, @Query("limit") limit: Int, @Query("offset") offset: Int)

    @POST("playlists/{playlist_id}/tracks")
    fun addTracksToPlaylist(@Path("playlist_id") playlistId: String, @Query("position") positionQuery: Int, @Query("uris") tracksUris: String, @Body urisBody: String, @Body positionBody: Int): UpdateTracksResponse //todo: check body's sending

    @PUT("playlists/{playlist_id}/tracks")
    fun updatePlaylistTracks(@Path("playlist_id") playlistId: String, @Query("uris") tracksUris: String, @Body changes: UpdatePlaylistTracksDto): UpdateTracksResponse

    @DELETE("playlists/{playlist_id}/tracks")
    fun deleteTracksFromPlaylist(@Path("playlist_id") playlistId: String, @Body changes: DeletePlaylistTracksDto): UpdateTracksResponse

    @GET("me/playlists")
    fun getCurrentUserPlaylists(@Query("limit") limit: Int, @Query("offset") offset: Int) : UserPlaylistsResponse

    @GET("users/{user_id}/playlists")
    fun getUserPlaylists(@Path("user_id") userId: String, @Query("limit") limit: Int, @Query("offset") offset: Int) : UserPlaylistsResponse

    @POST("users/{user_id}/playlists")
    fun createPlaylist(@Path("user_id") userId: String, @Body createPlaylist: CreatePlaylistRequestDto): CreatePlaylistResponse

    @GET("playlists/{playlist_id}/images")
    fun getPlaylistCoverImage(@Path("playlist_id") id: String): PlaylistImageResponse

    // todo: wtf
    @PUT("playlists/{playlist_id}/images")
    fun updatePlaylistCoverImage(@Path("playlist_id") id: String): PlaylistImageResponse

    // todo: not everything is required!!
    @GET("browse/featured-playlists")
    fun getFeaturedPlaylists(@Query("country") country: String, @Query("limit") limit: Int, @Query("locale") locale: String, @Query("offset") offset: Int, @Query("timestamp") timestamp: String): BrowsePlaylistsResponse

    @GET("browse/categories/{category_id}/playlists")
    fun getPlaylistsByCategory(@Path("category_id") userId: String, @Query("country") country: String, @Query("limit") limit: Int, @Query("offset") offset: Int): BrowsePlaylistsResponse



}
