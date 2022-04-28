package ru.itis.morefy.core.data.request

import com.google.gson.annotations.SerializedName

data class DeletePlaylistTracksDto (
    @SerializedName("uris")
    val uris: List<String>,
    @SerializedName("snapshot_id")
    private val snapshot_id: String,
)
