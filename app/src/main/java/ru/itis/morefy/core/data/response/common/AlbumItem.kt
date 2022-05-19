package ru.itis.morefy.core.data.response.common

data class AlbumItem (
    val album_type: String,
    val artists: List<ArtistShorted>,
    val available_markets: List<String>?, // might not be provided
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
