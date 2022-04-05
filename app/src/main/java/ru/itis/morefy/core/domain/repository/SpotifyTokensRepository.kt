package ru.itis.morefy.core.domain.repository

import ru.itis.morefy.core.domain.models.TokenContainer

/**
 * Spotify API based repository
 * https://developer.spotify.com/documentation/general/guides/authorization/code-flow/
 */
interface SpotifyTokensRepository {
    fun getRefreshedAccessToken(refreshToken: String): String?
    fun getTokensByCode(code: String): TokenContainer?
    fun getRedirectUri(): String
}
