package ru.itis.morefy.core.data.response.common

import ru.itis.morefy.core.data.response.playlist.Owner
import ru.itis.morefy.core.data.response.playlist.TracksShort

data class PlaylistItemShorted (
    val collaborative: Boolean,
    val description: String,
    val external_urls: ExternalUrls,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val owner: Owner,
    val public: Boolean,
    val snapshot_id: String,
    val tracks: TracksShort,
    val type: String,
    val uri: String
)
