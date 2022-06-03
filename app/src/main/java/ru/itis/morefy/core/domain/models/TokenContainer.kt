package ru.itis.morefy.core.domain.models

data class TokenContainer(
    var accessToken: String,
    val tokenType: String,
    val scope: String,
    val expiresIn: Int,
    val refreshToken: String,
)
