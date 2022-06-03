package ru.itis.morefy.core.data.tokens.net.response

import kotlinx.serialization.Serializable

@Serializable
data class RefreshedAccessTokenResponse(
    val access_token: String,
    val expires_in: Int,
    val scope: String,
    val token_type: String
)
