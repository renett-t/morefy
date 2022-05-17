package ru.itis.morefy.core.data.response.playlist

import ru.itis.morefy.core.data.response.common.ExternalUrls
import ru.itis.morefy.core.data.response.common.Followers

data class CreatePlaylistResponse(
    val collaborative: Boolean,
    val description: String,
    val external_urls: ExternalUrls,
    val followers: Followers,
    val href: String,
    val id: String,
    val name: String,
    val owner: Owner,
    val public: Boolean,
    val snapshot_id: String,
    val type: String,
    val uri: String
)
