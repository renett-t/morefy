package ru.itis.morefy.core.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.itis.morefy.core.data.response.recommendations.GenreSeedsResponse
import ru.itis.morefy.core.data.response.recommendations.RecommendationsResponse

interface SpotifyRecommendationsApi {

    // recommendations - https://developer.spotify.com/documentation/web-api/reference/#/operations/get-recommendations

    @GET("recommendations")
    fun getRecommendations(@Query("seed_artists") artists: String, @Query("seed_genres") genres: String, @Query("seed_tracks") tracks: String, @Query("market") market: String) : RecommendationsResponse

    @GET("recommendations")
    fun getRecommendations(@Query("seed_artists") artists: String, @Query("seed_genres") genres: String, @Query("seed_tracks") tracks: String, @Query("market") market: String, @Query("limit") limit: Int) : RecommendationsResponse

    @GET("recommendations")
    fun getRecommendations(@Query("seed_artists") artists: String, @Query("seed_genres") genres: String, @Query("seed_tracks") tracks: String, @Query("market") market: String, @Query("limit") limit: Int, @QueryMap recommendationsOptions: Map<String, String>) : RecommendationsResponse

    @GET("recommendations/available-genre-seeds")
    fun getAvailableGenreSeeds() : GenreSeedsResponse
}
