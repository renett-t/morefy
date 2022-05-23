package ru.itis.morefy.core.data.mapper

import android.util.Log
import ru.itis.morefy.core.data.response.playlist.UserPlaylistsResponse
import ru.itis.morefy.core.domain.models.Playlist
import ru.itis.morefy.core.domain.models.User
import javax.inject.Inject

class PlaylistsMapper @Inject constructor() {
    fun mapFrom(response: UserPlaylistsResponse): List<Playlist> {
        try {
            val list = ArrayList<Playlist>()

            for (item in response.items) {
                val user = User(item.owner.id, item.owner.display_name, item.owner.uri)
                var image: String? = null

                if (item.images.isNotEmpty())
                    image = item.images.last().url

                val playlist = Playlist(
                    item.id,
                    item.type,
                    item.tracks.total,
                    item.name,
                    image,
                    item.collaborative,
                    item.public,
                    user,
                    item.uri
                )

                list.add(playlist)
            }

            return list
        } catch (ex: Exception) {
            Log.e("MAPPING PLAYLISTS EX", ex.message ?: "message is absent")
            ex.printStackTrace()
            return emptyList()
        }
    }
}
