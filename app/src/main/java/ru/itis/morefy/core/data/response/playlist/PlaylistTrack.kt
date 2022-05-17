package ru.itis.morefy.core.data.response.playlist

import ru.itis.morefy.core.data.response.common.ArtistShorted
import ru.itis.morefy.core.data.response.common.ExternalIdsISRC
import ru.itis.morefy.core.data.response.common.ExternalUrls
import ru.itis.morefy.core.data.response.common.Restrictions

data class PlaylistTrack(
    val album: Album,
    val artists: List<ArtistShorted>,
    val disc_number: Int,
    val duration_ms: Int,
    val episode: Boolean,
    val explicit: Boolean,
    val external_ids: ExternalIdsISRC,
    val external_urls: ExternalUrls,
    val href: String,
    val id: String,
    val is_local: Boolean,
    val is_playable: Boolean,
    val name: String,
    val popularity: Int,
    val preview_url: String,
    val restrictions: Restrictions,
    val track: Boolean,
    val track_number: Int,
    val type: String,
    val uri: String
)
