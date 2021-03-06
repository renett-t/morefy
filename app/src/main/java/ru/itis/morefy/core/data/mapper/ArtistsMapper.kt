package ru.itis.morefy.core.data.mapper

import ru.itis.morefy.core.data.response.artist.ArtistsResponse
import ru.itis.morefy.core.data.response.artist.RelatedArtistsResponse
import ru.itis.morefy.core.data.response.common.ArtistResponse
import ru.itis.morefy.core.data.response.user.UserFollowedArtistsResponse
import ru.itis.morefy.core.data.response.user.UserTopArtistsResponse
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Genre
import javax.inject.Inject

class ArtistsMapper @Inject constructor() {

    fun mapFrom(response: UserTopArtistsResponse): List<Artist> {
        val list = ArrayList<Artist>()
        for (item in response.items) {
            list.add(getArtistFromResponse(item))
        }
        return list
    }

    fun mapFrom(response: UserFollowedArtistsResponse): List<Artist> {
        val list = ArrayList<Artist>()
        for (item in response.artists.items) {
            list.add(getArtistFromResponse(item))
        }
        return list
    }

    private fun getArtistFromResponse(item: ArtistResponse): Artist {
        val genres = ArrayList<Genre>()
        for (genre in item.genres) {
            genres.add(Genre(genre))
        }

        return Artist(
            item.id, item.name,
            item.followers.total, genres,
            item.images.first().url, item.popularity, item.uri
        )
    }

    fun mapFrom(response: ArtistResponse): Artist {
        val genres = ArrayList<Genre>()
        for (g: String in response.genres) {
            genres.add(Genre(g))
        }
        return Artist(
            response.id, response.name, response.followers.total,
            genres, response.images.first().url, response.popularity,
            response.uri
        )
    }

    fun mapFrom(response: ArtistsResponse): List<Artist> {
        val list = ArrayList<Artist>()
        for (item in response.artists) {
            list.add(mapFrom(item))
        }
        return list
    }

    fun mapFrom(response: RelatedArtistsResponse): List<Artist> {
        val list = ArrayList<Artist>()
        for (item in response.artists) {
            list.add(mapFrom(item))
        }
        return list
    }

    fun mapFrom(items: List<ArtistResponse>): List<Artist> {
        val list = ArrayList<Artist>()
        for (item in items) {
            list.add(mapFrom(item))
        }
        return list
    }
}
