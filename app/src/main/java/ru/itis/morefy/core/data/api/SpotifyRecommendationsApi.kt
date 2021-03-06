package ru.itis.morefy.core.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.itis.morefy.core.data.response.recommendations.CategoriesResponse
import ru.itis.morefy.core.data.response.recommendations.GenreSeedsResponse
import ru.itis.morefy.core.data.response.recommendations.RecommendationsResponse

interface SpotifyRecommendationsApi {

    // recommendations - https://developer.spotify.com/documentation/web-api/reference/#/operations/get-recommendations

    @GET("recommendations")
    suspend fun getRecommendations(
        @Query("seed_artists") artists: String,
        @Query("seed_genres") genres: String,
        @Query("seed_tracks") tracks: String,
        @Query("market") market: String
    ): RecommendationsResponse

    @GET("recommendations")
    suspend fun getRecommendationsLimited(
        @Query("seed_artists") artists: String,
        @Query("seed_genres") genres: String,
        @Query("seed_tracks") tracks: String,
        @Query("market") market: String,
        @Query("limit") limit: Int
    ): RecommendationsResponse

    @GET("recommendations")
    suspend fun getRecommendations(
        @Query("seed_artists") artists: String,
        @Query("seed_genres") genres: String,
        @Query("seed_tracks") tracks: String,
        @Query("market") market: String,
        @Query("limit") limit: Int,
        @QueryMap recommendationsOptions: Map<String, String>
    ): RecommendationsResponse

    @GET("recommendations/available-genre-seeds")
    suspend fun getAvailableGenreSeeds(): GenreSeedsResponse

    @GET("browse/categories")
    suspend fun getCategoriesGlobal(): CategoriesResponse

    @GET("browse/categories")
    suspend fun getCategoriesGlobalLocalised(
        @Query("locale") locale: String,
    ): CategoriesResponse

    @GET("browse/categories")
    suspend fun getCategoriesGlobalLimited(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): CategoriesResponse

    @GET("browse/categories")
    suspend fun getCategoriesGlobalLimitedLocalised(
        @Query("limit") limit: Int,
        @Query("locale") locale: String,
        @Query("offset") offset: Int,
    ): CategoriesResponse

    @GET("browse/categories")
    suspend fun getCategoriesForCountry(
        @Query("country") country: String,
        @Query("locale") locale: String,
    ): CategoriesResponse

    @GET("browse/categories")
    suspend fun getCategoriesForCountryLimited(
        @Query("country") country: String,
        @Query("locale") locale: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): CategoriesResponse

}
