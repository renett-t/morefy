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
    fun getTrackById(@Path("id") id: String, @Query("market") market: String): Track

    @GET("tracks")
    fun getTracksByIds(@Query("ids") id: String, @Query("market") market: String): TracksResponse

    @GET("me/tracks")
    fun getUserSavedTracks(@Query("market") market: String): UserSavedTracksResponse

    @GET("me/tracks")
    fun getUserSavedTracksLimited(
        @Query("market") market: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): UserSavedTracksResponse

    @PUT("me/tracks")
    fun saveTrackForUser(
        @Body ids: IdsDto
    )

    @DELETE("me/tracks")
    fun deleteTrackForUser(
        @Body ids: IdsDto
    )

    @GET("me/tracks/contains")
    fun checkUserSavedTracks(@Query("ids") ids: String): CheckSavedResponse

    @GET("audio-features/{id}")
    fun getTrackAudioFeaturesById(@Path("id") id: String): TrackAudioFeatures

    @GET("audio-features")
    fun getTracksAudioFeaturesByIds(@Query("ids") ids: String): TracksAudioFeatures

    @GET("audio-analysis/{id}")
    fun getTrackAudioAnalysisById(@Path("id") id: String): TrackAudioAnalysis

}
