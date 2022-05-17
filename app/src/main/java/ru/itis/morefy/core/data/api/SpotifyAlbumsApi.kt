package ru.itis.morefy.core.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.itis.morefy.core.data.response.album.*
import ru.itis.morefy.core.data.response.common.CheckSavedResponse

interface SpotifyAlbumsApi {
    // albums - https://developer.spotify.com/documentation/web-api/reference/#/operations/get-an-album

    @GET("albums/{id}")
    fun getAlbumById(@Path("id") id: String, @Query("market") market: String): AlbumResponse

    @GET("albums")
    fun getAlbumsByIds(@Query("ids") ids: String, @Query("market") market: String): AlbumsResponse

    @GET("albums/{id}/tracks")
    fun getTracksByAlbumId(
        @Path("id") ids: String,
        @Query("market") market: String
    ): AlbumTracksResponse

    @GET("albums/{ids}/tracks")
    fun getTracksByAlbumId(
        @Path("id") ids: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("market") market: String
    ): AlbumTracksResponse

    @GET("me/albums")
    fun getUserSavedAlbums(@Query("market") market: String): UserSavedAlbumsResponse

    @GET("me/albums")
    fun getUserSavedAlbums(
        @Query("market") market: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): UserSavedAlbumsResponse

    @GET("me/albums/contains")
    fun checkIfAlbumsSaved(@Query("ids") albumsIds: String): CheckSavedResponse

    @GET("browse/new-releases")
    fun getNewReleasesAlbums(@Query("country") country: String): NewReleasesResponse

    @GET("browse/new-releases")
    fun getNewReleasesAlbums(
        @Query("country") country: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): NewReleasesResponse
}
