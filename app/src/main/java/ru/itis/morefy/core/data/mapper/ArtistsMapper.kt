package ru.itis.morefy.core.data.mapper

import ru.itis.morefy.core.data.response.user.UserTopArtistsResponse
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Genre
import javax.inject.Inject

class ArtistsMapper @Inject constructor() {

    fun mapFrom(response: UserTopArtistsResponse): List<Artist> {
        val list = ArrayList<Artist>()
        for (item in response.items) {
            val genres = ArrayList<Genre>()
            for (genre in item.genres) {
                genres.add(Genre(genre))
            }

            val artist = Artist(
                item.id, item.name,
                item.followers.total, genres,
                item.images.last().url, item.popularity, item.uri
            )

            list.add(artist)
        }
        return list
    }

}
