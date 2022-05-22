package ru.itis.morefy.core.domain.repository

import android.net.Uri
import ru.itis.morefy.core.domain.models.TokenContainer

/**
 * Spotify API based repository
 * https://developer.spotify.com/documentation/general/guides/authorization/code-flow/
 */
interface SpotifyTokensRepository {
    fun getRefreshedAccessToken(refreshToken: String): String?
    suspend fun getTokensByCode(code: String): TokenContainer?
    fun getRedirectUri(): Uri
}
