package ru.itis.morefy.core.data.request

import com.google.gson.annotations.SerializedName

data class AddPlaylistTracksDto (
    @SerializedName("uris")
    val uris: List<String>,
    @SerializedName("position")
    val position: Int
)
// example:
//{
//    "uris": ["spotify:track:55ecOOZmfDWmIUpHoxCWmE", "spotify:track:46AEVAiUDZ0ZQ4GNQp7C0g", "spotify:track:47RyOrXqLydmsPrsVU7nTw"],
//    "position": 0
//}
