package ru.itis.morefy.core.data.request

data class DeletePlaylistTracksDto (
    private val tracks: List<TracksRequestDto>,
    private val snapshot_id: String,
)

data class TracksRequestDto (
    private val uri: String,
)
