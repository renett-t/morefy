package ru.itis.morefy.core.data.tokens.net.response

import kotlinx.serialization.Serializable

@Serializable
data class SpotifyTokensResponse(
    var access_token: String,
    val token_type: String,
    val expires_in: Int,
    val refresh_token: String,
    val scope: String,
)
