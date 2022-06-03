package ru.itis.morefy.search.data.repository

import android.util.Log
import retrofit2.HttpException
import ru.itis.morefy.core.data.api.DEFAULT_MARKET
import ru.itis.morefy.core.data.api.SpotifySearchApi
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.search.domain.repository.SearchQueryRepository
import javax.inject.Inject

class SpotifySearchQueryRepositoryImpl @Inject constructor(
    private val spotifySearchApi: SpotifySearchApi,
    private val queriesMapper: QueriesMapper
) : SearchQueryRepository {

    override suspend fun getArtistsByQuery(query: String, amount: Int): List<Artist> {
        try {
            return queriesMapper.mapFrom(
                spotifySearchApi.searchArtistsByQuery(
                    query,
                    DEFAULT_MARKET,
                    amount,
                    0
                )
            )
        } catch (e: HttpException) {
            Log.e("SearchQueryRepository", "Get Artists By Query Exception: ${e.message()}")
            throw e
        }
    }

    override suspend fun getTracksByQuery(query: String, amount: Int): List<Track> {
        try {
            return queriesMapper.mapFrom(
                spotifySearchApi.searchTracksByQuery(
                    query,
                    DEFAULT_MARKET,
                    amount,
                    0
                )
            )
        } catch (e: HttpException) {
            Log.e("SearchQueryRepository", "Get Tracks By Query Exception: ${e.message()}")
            throw e
        }
    }
}
