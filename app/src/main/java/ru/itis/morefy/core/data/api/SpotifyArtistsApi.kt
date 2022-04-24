package ru.itis.morefy.core.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.itis.morefy.core.data.response.artist.*

interface SpotifyArtistsApi {

    // artists - https://developer.spotify.com/documentation/web-api/reference/#/operations/get-an-artist

    @GET("artists/{id}")
    fun getArtistById(@Path("id") id: Int) : ArtistResponse

    @GET("artists")
    fun getArtistsByIds(@Query("ids") id: Int) : ArtistsResponse

    @GET("artists/{id}/albums")
    fun getAlbumsByArtistId(@Path("id") id: Int, @Query("include_groups") includeGroups: String, @Query("market") market: String) : ArtistAlbumsResponse

    @GET("artists/{id}/albums")
    fun getAlbumsByArtistId(@Path("id") id: Int, @Query("include_groups") includeGroups: String, @Query("limit") limit: Int, @Query("offset") offset: Int, @Query("market") market: String) : ArtistAlbumsResponse

    @GET("artists/{id}/top-tracks")
    fun getTopTracksByArtistId(@Path("id") id: Int, @Query("market") market: String) : ArtistTopTracksResponse

    @GET("artists/{id}/related-artists")
    fun getRelatedArtistsByArtistId(@Path("id") id: Int) : RelatedArtistsResponse


}
