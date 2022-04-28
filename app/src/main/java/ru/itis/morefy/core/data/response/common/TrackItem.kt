package ru.itis.morefy.core.data.response.common

data class TrackItem(
    val id: String,
    val name: String,
    val artists: List<ArtistShorted>,
    val disc_number: Int,
    val duration_ms: Int,
    val explicit: Boolean,
    val is_local: Boolean,
    val is_playable: Boolean,
    val preview_url: String,
    val track_number: Int,
    val href: String,
    val uri: String
)
