package ru.itis.morefy.core.data.mapper

import android.util.Log
import ru.itis.morefy.core.data.response.common.ArtistShorted
import ru.itis.morefy.core.data.response.player.PlayerResponse
import ru.itis.morefy.core.data.response.track.TracksResponse
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
        return try {
            for (item in response.items) {
                list.add(getTrackFromResponse(item))
            }
            list
        } catch (ex: Exception) {
            Log.e("TOP TRACKS MAPPING EXCEPTION", "Happened in TracksMapper, message = ${ex.message}")
            listOf()
        }
    }

    fun mapFrom(response: PlayerResponse): List<Track> {
        val list = ArrayList<Track>()

        for (playedTrack in response.items) {
            list.add(getTrackFromResponse(playedTrack.track))
        }
        return list
    }

    fun mapFrom(response: ru.itis.morefy.core.data.response.common.Track): Track {
        Log.e("TRACK", "GOT RESULT. MAPPING TRACK!")
        return getTrackFromResponse(response)
    }


    fun mapFrom(response: TracksResponse): List<Track> {
        val list = ArrayList<Track>()
        for (track in response.tracks) {
            list.add(getTrackFromResponse(track))
        }
        return list
    }


    private fun getTrackFromResponse(response: ru.itis.morefy.core.data.response.common.Track): Track {
        val album = Album(
            response.album.id, response.album.album_type,
            response.album.total_tracks, response.album.name,
            response.album.images.first().url,
            dateFormatter.getDate(response.album.release_date),
            response.album.uri
        )

        val list = ArrayList<Artist>()
        for (art in response.artists) {
            list.add(getArtistFromResponse(art))
        }

        return Track(
            response.id, album, list,
            response.duration_ms, response.explicit,
            response.name, response.popularity,
            response.preview_url, response.uri
        )
    }

    private fun getArtistFromResponse(art: ArtistShorted): Artist {
        return Artist(art.id, art.name, art.uri)
    }

    fun mapFrom(tracks: List<ru.itis.morefy.core.data.response.common.Track>): List<Track> {
        val list = ArrayList<Track>()
        for (track in tracks) {
            list.add(getTrackFromResponse(track))
        }
        return list
    }
}
