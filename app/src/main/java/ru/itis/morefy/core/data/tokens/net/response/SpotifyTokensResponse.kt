package ru.itis.morefy.core.data.tokens.net.response

data class SpotifyTokensResponse (
    var accessToken: String,
    val tokenType: String,
    val scope: String,
    val expiresIn: Int,
    val refreshToken: String,
)
