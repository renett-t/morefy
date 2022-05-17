package ru.itis.morefy.core.data.response.album

import ru.itis.morefy.core.data.response.common.Album

data class SavedAlbumItem(
    val added_at: String,
    val album: Album
)
