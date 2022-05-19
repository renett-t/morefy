package ru.itis.morefy.core.data.api

import retrofit2.http.*
import ru.itis.morefy.core.data.request.IdsDto
import ru.itis.morefy.core.data.response.common.CheckSavedResponse
import ru.itis.morefy.core.data.response.common.Track
import ru.itis.morefy.core.data.response.track.*
import ru.itis.morefy.core.data.response.track.audio.TrackAudioAnalysis

interface SpotifyTracksApi {

    // tracks - https://developer.spotify.com/documentation/web-api/reference/#/operations/get-track

    @GET("tracks/{id}")
    suspend fun getTrackById(@Path("id") id: String, @Query("market") market: String): Track

    @GET("tracks")
    suspend fun getTracksByIds(@Query("ids") id: String, @Query("market") market: String): TracksResponse

    @GET("me/tracks")
    suspend fun getUserSavedTracks(@Query("market") market: String): UserSavedTracksResponse

    @GET("me/tracks")
    suspend fun getUserSavedTracksLimited(
        @Query("market") market: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): UserSavedTracksResponse

    @PUT("me/tracks")
    suspend fun saveTrackForUser(
        @Body ids: IdsDto
    )

    @DELETE("me/tracks")
    suspend fun deleteTrackForUser(
        @Body ids: IdsDto
    )

    @GET("me/tracks/contains")
    suspend fun checkUserSavedTracks(@Query("ids") ids: String): CheckSavedResponse

    @GET("audio-features/{id}")
    suspend fun getTrackAudioFeaturesById(@Path("id") id: String): TrackAudioFeatures

    @GET("audio-features")
    suspend fun getTracksAudioFeaturesByIds(@Query("ids") ids: String): TracksAudioFeatures

    @GET("audio-analysis/{id}")
    suspend fun getTrackAudioAnalysisById(@Path("id") id: String): TrackAudioAnalysis

}
