package ru.itis.morefy.core.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.itis.morefy.core.data.response.search.album.SearchAlbumsResponse
import ru.itis.morefy.core.data.response.search.SearchQueryResponse
import ru.itis.morefy.core.data.response.search.artist.SearchArtistsResponse
import ru.itis.morefy.core.data.response.search.playlist.SearchPlaylistsResponse
import ru.itis.morefy.core.data.response.search.track.SearchTracksResponse

interface SpotifySearchApi {

    // search - https://developer.spotify.com/documentation/web-api/reference/#/operations/search

    @GET("search")
    suspend fun searchByQuery(
        @Query("q") q: String,
        @Query("type") types: String,
        @Query("market") market: String
    ): SearchQueryResponse

    @GET("search")
    suspend fun searchByQueryLimited(
        @Query("q") q: String,
        @Query("type") types: String,
        @Query("market") market: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): SearchQueryResponse

    @GET("search?type=track")
    suspend fun searchTracksByQuery(
        @Query("q") q: String,
        @Query("market") market: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): SearchTracksResponse

    @GET("search?type=artist")
    suspend fun searchArtistsByQuery(
        @Query("q") q: String,
        @Query("market") market: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): SearchArtistsResponse

    @GET("search?type=album")
    suspend fun searchAlbumsByQuery(
        @Query("q") q: String,
        @Query("market") market: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): SearchAlbumsResponse

    @GET("search?type=playlist")
    suspend fun searchPlaylistsByQuery(
        @Query("q") q: String,
        @Query("market") market: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): SearchPlaylistsResponse

}
