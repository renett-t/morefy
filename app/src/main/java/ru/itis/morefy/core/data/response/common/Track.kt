package ru.itis.morefy.core.data.response.common

data class Track(
    val album: AlbumItem,
    val artists: List<ArtistShorted>,
    val disc_number: Int,
    val duration_ms: Int,
    val explicit: Boolean,
    val external_ids: ExternalIdsISRC,
    val external_urls: ExternalUrls,
    val href: String,
    val id: String,
    val is_local: Boolean,
    val is_playable: Boolean,
    val name: String,
    val popularity: Int,
    val preview_url: String?,
    val track_number: Int,
    val type: String,
    val uri: String
)
