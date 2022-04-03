package ru.itis.morefy.core.domain.repository

import ru.itis.morefy.core.domain.models.TokenContainer

interface SpotifyTokensRepository {
    fun getRefreshedAccessToken(refreshToken: String): String?
    fun getTokensByCode(code: String, redirectUrl: String): TokenContainer?
}
