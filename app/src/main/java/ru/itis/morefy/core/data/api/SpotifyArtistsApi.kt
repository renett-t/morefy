package ru.itis.morefy.core.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.itis.morefy.core.data.response.artist.*
import ru.itis.morefy.core.data.response.common.ArtistResponse

interface SpotifyArtistsApi {

    // artists - https://developer.spotify.com/documentation/web-api/reference/#/operations/get-an-artist

    @GET("artists/{id}")
    suspend fun getArtistById(@Path("id") id: Int): ArtistResponse

    @GET("artists")
    suspend fun getArtistsByIds(@Query("ids") id: Int): ArtistsResponse

    @GET("artists/{id}/albums")
    suspend fun getAlbumsByArtistId(@Query("market") market: String): ArtistAlbumsResponse

    @GET("artists/{id}/albums")
    suspend fun getAlbumsByArtistId(
        @Path("id") id: Int,
        @Query("include_groups") includeGroups: String,
        @Query("market") market: String
    ): ArtistAlbumsResponse

    @GET("artists/{id}/albums")
    suspend fun getAlbumsByArtistId(
        @Path("id") id: Int,
        @Query("include_groups") includeGroups: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("market") market: String
    ): ArtistAlbumsResponse

    @GET("artists/{id}/top-tracks")
    suspend fun getTopTracksByArtistId(
        @Path("id") id: Int,
        @Query("market") market: String
    ): ArtistTopTracksResponse

    @GET("artists/{id}/related-artists")
    suspend fun getRelatedArtistsByArtistId(@Path("id") id: Int): RelatedArtistsResponse

}
