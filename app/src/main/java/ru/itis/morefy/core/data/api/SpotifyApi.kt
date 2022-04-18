package ru.itis.morefy.core.data.api

import retrofit2.http.*
import ru.itis.morefy.core.data.response.PlaylistResponse
import ru.itis.morefy.core.data.response.recommendations.GenreSeedsResponse
import ru.itis.morefy.core.data.response.search.SearchQueryResponse
import ru.itis.morefy.core.data.response.album.*
import ru.itis.morefy.core.data.response.artist.*
import ru.itis.morefy.core.data.response.recommendations.RecommendationsResponse
import ru.itis.morefy.core.data.response.track.*
import ru.itis.morefy.core.data.response.user.*

interface SpotifyApi {

    // albums - https://developer.spotify.com/documentation/web-api/reference/#/operations/get-an-album

    @GET("albums/{id}")
    fun getAlbumById(@Path("id") id: String, @Query("market") market: String) : AlbumResponse

    @GET("albums")
    fun getAlbumsByIds(@Query("ids") ids: String, @Query("market") market: String) : AlbumsResponse

    @GET("albums/{ids}/tracks")
    fun getTracksByAlbumId(@Path("id") ids: String, @Query("market") market: String) : AlbumTracksResponse

    @GET("albums/{ids}/tracks")
    fun getTracksByAlbumId(@Path("id") ids: String, @Query("limit") limit: Int, @Query("offset") offset: Int, @Query("market") market: String) : AlbumTracksResponse

    @GET("me/albums")
    fun getUserSavedAlbums(@Query("market") market: String) : UserSavedAlbumsResponse

    @GET("me/albums")
    fun getUserSavedAlbums(@Query("market") market: String, @Query("limit") limit: Int, @Query("offset") offset: Int) : UserSavedAlbumsResponse

    @GET("browse/new-releases")
    fun getNewReleasesAlbums(@Query("country") country: String) : NewReleasesAlbumsResponse

    @GET("me/albums/contains")
    fun checkIfAlbumsSaved(@Query("ids") albumsIds: String) : CheckSavedAlbumsResponse

    @GET("browse/new-releases")
    fun getNewReleasesAlbums(@Query("country") country: String, @Query("limit") limit: Int, @Query("offset") offset: Int) : NewReleasesAlbumsResponse


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


    // recommendations - https://developer.spotify.com/documentation/web-api/reference/#/operations/get-recommendations

    @GET("recommendations")
    fun getRecommendations(@Query("seed_artists") artists: String, @Query("seed_genres") genres: String, @Query("seed_tracks") tracks: String, @Query("market") market: String) : RecommendationsResponse

    @GET("recommendations")
    fun getRecommendations(@Query("seed_artists") artists: String, @Query("seed_genres") genres: String, @Query("seed_tracks") tracks: String, @Query("market") market: String, @Query("limit") limit: Int) : RecommendationsResponse

    @GET("recommendations")
    fun getRecommendations(@Query("seed_artists") artists: String, @Query("seed_genres") genres: String, @Query("seed_tracks") tracks: String, @Query("market") market: String, @Query("limit") limit: Int, @QueryMap recommendationsOptions: Map<String, String>) : RecommendationsResponse

    @GET("recommendations/available-genre-seeds")
    fun getAvailableGenreSeeds() : GenreSeedsResponse


    // search - https://developer.spotify.com/documentation/web-api/reference/#/operations/search

    @GET("search")
    fun searchByQuery(@Query("q") q: String, @Query("type") types: String, @Query("market") market: String) : SearchQueryResponse

    @GET("search")
    fun searchByQuery(@Query("q") q: String, @Query("type") types: String, @Query("market") market: String, @Query("limit") limit: Int, @Query("offset") offset: Int) : SearchQueryResponse

    @GET("search?type=track")
    fun searchTracksByQuery(@Query("q") q: String, @Query("market") market: String, @Query("limit") limit: Int, @Query("offset") offset: Int) : SearchQueryResponse

    @GET("search?type=artist")
    fun searchArtistsByQuery(@Query("q") q: String, @Query("market") market: String, @Query("limit") limit: Int, @Query("offset") offset: Int) : SearchQueryResponse

    @GET("search?type=album")
    fun searchAlbumsByQuery(@Query("q") q: String, @Query("market") market: String, @Query("limit") limit: Int, @Query("offset") offset: Int) : SearchQueryResponse

    @GET("search?type=playlist")
    fun searchPlaylistsByQuery(@Query("q") q: String, @Query("market") market: String, @Query("limit") limit: Int, @Query("offset") offset: Int) : SearchQueryResponse


    // users - https://developer.spotify.com/documentation/web-api/reference/#/operations/get-current-users-profile

    @GET("me")
    fun getUserProfile() : UserProfileResponse

    @GET("users/{user_id}")
    fun getUserProfileByUserId(@Path("user_id") userId: String) : CurrentUserProfileResponse

    @GET("me/top/artists")
    fun getUserTopArtists(@Query("time_range") timeRange: String) : UserTopArtistsResponse

    @GET("me/top/artists")
    fun getUserTopArtists(@Query("time_range") timeRange: String, @Query("limit") limit: Int, @Query("offset") offset: Int) : UserTopArtistsResponse

    @GET("me/top/tracks")
    fun getUserTopTracks(@Query("time_range") timeRange: String) : UserTopTracksResponse

    @GET("me/top/tracks")
    fun getUserTopTracks(@Query("time_range") timeRange: String, @Query("limit") limit: Int, @Query("offset") offset: Int) : UserTopTracksResponse

    @PUT("playlists/{playlist_id}/followers")
    fun followPlaylist(@Path("playlist_id") playlistId: String)

    @PUT("playlists/{playlist_id}/followers")
    fun followPlaylist(@Path("playlist_id") playlistId: String, @Body isPublic: Boolean)

    @DELETE("playlists/{playlist_id}/followers")
    fun unfollowPlaylist(@Path("playlist_id") playlistId: String)

    @GET("me/following?type=artist")
    fun getUserFollowedArtists() : UserFollowedArtistsResponse

    @GET("me/following?type=artist")
    fun getUserFollowedArtists(@Query("limit") limit: Int) : UserFollowedArtistsResponse

    @GET("me/following?type=artist")
    fun getUserFollowedArtists(@Query("after") afterArtistId: String) : UserFollowedArtistsResponse

    @GET("me/following?type=artist")
    fun getUserFollowedArtists(@Query("after") afterArtistId: String, @Query("limit") limit: Int) : UserFollowedArtistsResponse

    @PUT("me/following?type=artist")
    fun followArtist(@Query("ids") idsQuery: String, @Body ids: Array<String>) // todo: check serialization format

    @PUT("me/following?type=user")
    fun followUser(@Query("ids") idsQuery: String, @Body ids: Array<String>) // todo: check serialization format

    @DELETE("me/following?type=artist")
    fun unfollowArtist(@Query("ids") idsQuery: String, @Body ids: Array<String>) // todo: check serialization format

    @DELETE("me/following?type=user")
    fun unfollowUser(@Query("ids") idsQuery: String, @Body ids: Array<String>) // todo: check serialization format

    @GET("me/following/contains?type=artist")
    fun checkIfUserFollowsArtist(@Query("ids") ids: String) : CheckFollowingResponse

    @GET("me/following/contains?type=user")
    fun checkIfUserFollowsUser(@Query("ids") ids: String) : CheckFollowingResponse

    @GET("playlists/{playlist_id}/followers/contains ")
    fun checkIfUsersFollowsPlaylist(@Path("playlist_id") playlistId: String, @Query("ids") userIds: String) : CheckFollowingResponse


    // playlists - https://developer.spotify.com/documentation/web-api/reference/#/operations/get-playlist

    @GET("playlists/{playlist_id}")
    fun getPlaylistById(@Path("playlist_id") id: String, @Query("market") market: String) : PlaylistResponse

    @PUT("playlists/{playlist_id}")
    fun updatePlaylistDetailsById(@Path("playlist_id") id: String, @Body name: String, @Body public: Boolean, @Body collaborative: Boolean, @Body description: String)

    @GET("playlists/{playlist_id}/tracks")
    fun getPlaylistTracks(@Path("playlist_id") id: String, @Query("market") market: String)

    @GET("playlists/{playlist_id}/tracks")
    fun getPlaylistTracks(@Path("playlist_id") id: String, @Query("market") market: String, @Query("limit") limit: Int, @Query("offset") offset: Int)

    // todo: add all methods related to playlist
    // todo: create multiple api interfaces insted of this mess??
    // https://developer.spotify.com/documentation/web-api/reference/#/operations/add-tracks-to-playlist
}
