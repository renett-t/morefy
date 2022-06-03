package ru.itis.morefy.core.data.repository

import android.util.Log
import retrofit2.HttpException
import ru.itis.morefy.core.data.api.DEFAULT_MARKET
import ru.itis.morefy.core.data.api.MAX_LIMIT_AMOUNT
import ru.itis.morefy.core.data.api.SpotifyArtistsApi
import ru.itis.morefy.core.data.mapper.ArtistsMapper
import ru.itis.morefy.core.data.mapper.TracksMapper
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.core.domain.repository.ArtistsRepository
import javax.inject.Inject

class SpotifyArtistsRepositoryImpl @Inject constructor(
    private val artistsApi: SpotifyArtistsApi,
    private val artistsMapper: ArtistsMapper,
    private val tracksMapper: TracksMapper
): ArtistsRepository {

    override suspend fun getArtistById(id: String): Artist {
        try {
            return artistsMapper.mapFrom(artistsApi.getArtistById(id))
        } catch (e: HttpException) {
            Log.e("ArtistsRepository", "Get Artist By Id Exception: ${e.message()}")
            throw e
        }
    }

    override suspend fun getArtistsByIds(ids: List<String>): List<Artist> {
        try {
            if (ids.size > MAX_LIMIT_AMOUNT) {
                (TODO())
            } else {
                return artistsMapper.mapFrom(artistsApi.getArtistsByIds(getIdsParameter(ids)))
            }
        } catch (e: HttpException) {
            Log.e("ArtistsRepository", "Get Artists By Ids Exception: ${e.message()}")
            throw e
        }
    }

    override suspend fun getSimilarArtists(id: String): List<Artist> {
        try {
            return artistsMapper.mapFrom(artistsApi.getRelatedArtistsByArtistId((id)))
        } catch (e: HttpException) {
            Log.e("ArtistsRepository", "Get Related Artists By Artist Id Exception: ${e.message()}")
            throw e
        }
    }

    override suspend fun getArtistPopularTracks(id: String): List<Track> {
        try {
            return tracksMapper.mapFrom(artistsApi.getTopTracksByArtistId(id, DEFAULT_MARKET).tracks)
        } catch (e: HttpException) {
            Log.e("ArtistsRepository", "Get Artists By Ids Exception: ${e.message()}")
            throw e
        }
    }

    private fun getIdsParameter(ids: List<String>): String {
        return ids.joinToString(separator = ",")
    }
}
