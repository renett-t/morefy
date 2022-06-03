package ru.itis.morefy.core.data.response.playlist

import com.google.gson.annotations.SerializedName

data class PlaylistUpdateResponse(
    @SerializedName("snapshot_id")
    val snapshotId: String,
)
