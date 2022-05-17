package ru.itis.morefy.core.data.response.playlist

import ru.itis.morefy.core.data.response.common.ArtistShorted
import ru.itis.morefy.core.data.response.common.ExternalUrls
import ru.itis.morefy.core.data.response.common.Image

data class Album(
    val album_type: String,
    val artists: List<ArtistShorted>,
    val external_urls: ExternalUrls,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val release_date: String,
    val release_date_precision: String,
    val total_tracks: Int,
    val type: String,
    val uri: String
)
