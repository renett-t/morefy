package ru.itis.morefy.core.data.response.playlist

import ru.itis.morefy.core.data.response.common.ExternalUrls
import ru.itis.morefy.core.data.response.common.Followers
import ru.itis.morefy.core.data.response.common.Image

data class PlaylistResponse (
    val collaborative: Boolean,
    val description: String,
    val external_urls: ExternalUrls,
    val followers: Followers,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val owner: Owner,
    val public: Boolean,
    val snapshot_id: String,
    val tracks: PlaylistTracks,
    val type: String,
    val uri: String
)
