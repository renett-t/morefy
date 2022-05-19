package ru.itis.morefy.core.domain.repository

import ru.itis.morefy.core.domain.models.TokenContainer

/**
 * Spotify API based repository
 * https://developer.spotify.com/documentation/general/guides/authorization/code-flow/
 */
interface SpotifyTokensRepository {
    suspend fun getRefreshedAccessToken(refreshToken: String): String?
    suspend fun getTokensByCode(code: String): TokenContainer?
    fun getRedirectUri(): String
}
