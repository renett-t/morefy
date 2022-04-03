package ru.itis.morefy.core.domain.models

data class TokenContainer(
    private val refreshToken: String,
    private var accessToken: String
) {

}
