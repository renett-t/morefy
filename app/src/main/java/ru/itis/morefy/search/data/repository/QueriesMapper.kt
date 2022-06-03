package ru.itis.morefy.search.data.repository

import ru.itis.morefy.core.data.mapper.ArtistsMapper
import ru.itis.morefy.core.data.mapper.TracksMapper
import ru.itis.morefy.core.data.response.search.artist.SearchArtistsResponse
import ru.itis.morefy.core.data.response.search.track.SearchTracksResponse
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Track
import javax.inject.Inject

class QueriesMapper @Inject constructor(
    private val artistsMapper: ArtistsMapper,
    private val tracksMapper: TracksMapper
) {

    fun mapFrom(response: SearchArtistsResponse): List<Artist> {
        return artistsMapper.mapFrom(response.artists.items)
    }

    fun mapFrom(response: SearchTracksResponse): List<Track> {
        return tracksMapper.mapFrom(response.tracks.items)
    }
}
