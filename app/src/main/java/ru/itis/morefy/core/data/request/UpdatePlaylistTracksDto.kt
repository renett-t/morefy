package ru.itis.morefy.core.data.request

import com.google.gson.annotations.SerializedName

data class UpdatePlaylistTracksDto(
    @SerializedName("uris")
    val uris: List<String>,
    @SerializedName("range_start")
    val range_start: Int,
    @SerializedName("insert_before")
    val insert_before: Int,
    @SerializedName("range_length")
    val range_length: Int,
    @SerializedName("snapshot_id")
    val snapshot_id: String
)
