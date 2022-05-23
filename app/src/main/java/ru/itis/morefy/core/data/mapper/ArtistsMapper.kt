package ru.itis.morefy.core.data.mapper

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
            item.images.last().url, item.popularity, item.uri
        )
    }
}
