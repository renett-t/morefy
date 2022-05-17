package ru.itis.morefy.core.data.mapper

import ru.itis.morefy.core.data.response.user.UserTopTracksResponse
import ru.itis.morefy.core.domain.models.Album
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Track
import javax.inject.Inject

class TracksMapper @Inject constructor(
    private val dateFormatter: DateFormatter
) {

    fun mapFrom(response: UserTopTracksResponse): List<Track> {
        val list = ArrayList<Track>()

        for (item in response.items) {
            val album = Album(
                item.album.id, item.album.album_type,
                item.album.total_tracks, item.album.name,
                item.album.images.last().url, null, null,
                dateFormatter.getDate(item.album.release_date), null, null,
                item.album.uri
            )

            val artists = ArrayList<Artist>()
            for (artist in item.artists) {
                val newArtist = Artist(
                    artist.id, artist.name, -1,
                    null, null, -1, artist.uri
                )
                artists.add(newArtist)
            }

            val track = Track(
                item.id, album, artists,
                item.duration_ms, item.explicit,
                item.name, item.popularity, null,
                item.preview_url, item.uri
            )

            list.add(track)
        }
        return list
    }


}
