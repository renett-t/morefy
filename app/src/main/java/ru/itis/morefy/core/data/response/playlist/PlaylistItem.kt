package ru.itis.morefy.core.data.response.playlist

data class PlaylistItem(
    val added_at: String,
    val added_by: AddedBy,
    val is_local: Boolean,
    val track: PlaylistTrack,
)
