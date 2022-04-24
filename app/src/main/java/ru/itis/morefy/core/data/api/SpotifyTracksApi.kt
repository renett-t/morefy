package ru.itis.morefy.core.data.api

import retrofit2.http.*
import ru.itis.morefy.core.data.response.track.*

interface SpotifyTracksApi {

    // tracks - https://developer.spotify.com/documentation/web-api/reference/#/operations/get-track

    @GET("tracks/{id}")
    fun getTrackById(@Path("id") id: String, @Query("market") market: String) : TrackResponse

    @GET("tracks")
    fun getTracksByIds(@Query("ids") id: String, @Query("market") market: String) : TracksResponse

    @GET("me/tracks")
    fun getUserSavedTracks(@Query("market") market: String) : UserSavedTracksResponse

    @GET("me/tracks")
    fun getUserSavedTracks(@Query("market") market: String, @Query("limit") limit: Int, @Query("offset") offset: Int) : UserSavedTracksResponse

    @PUT("me/tracks")
    fun saveTrackForUser(@Query("ids") idsQuery: String, @Body ids: Array<String>) // todo: check body serialization

    @DELETE("me/tracks")
    fun deleteTrackForUser(@Query("ids") idsQuery: String, @Body ids: Array<String>) // todo: check body serialization

    @GET("audio-features")
    fun getTracksAudioFeaturesByIds(@Query("ids") ids: String) : TracksAudioFeatures

    @GET("audio-features/{id}")
    fun getTrackAudioFeaturesById(@Path("id") id: String) : TracksAudioFeatures

    @GET("audio-analysis/{id}")
    fun getTrackAudioAnalysisById(@Path("id") id: String) : TracksAudioAnalysis


}
