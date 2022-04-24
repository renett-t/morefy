package ru.itis.morefy.core.data.request

data class UpdatePlaylistTracksDto(
    private val uris: Array<String>,
    private val range_start: Int,
    private val insert_before: Int,
    private val range_length: Int,
    private val snapshot_id: String
)
