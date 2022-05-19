package ru.itis.morefy.core.domain.service

import ru.itis.morefy.core.domain.models.TokenContainer
import ru.itis.morefy.core.domain.repository.AuthorizationRepository
import ru.itis.morefy.core.domain.repository.SpotifyTokensRepository
import javax.inject.Inject

class RefreshTokenService @Inject constructor(
    private val authorizationRepository: AuthorizationRepository,
    private val spotifyTokensRepository: SpotifyTokensRepository
) {

    fun isAuthenticated(): Boolean {
        return authorizationRepository.isTokenSaved()
    }

    fun getTokens(): TokenContainer {
        return authorizationRepository.getTokens()
    }

    fun updateTokens() {
        val current = getTokens()
        val refreshedToken = spotifyTokensRepository.getRefreshedAccessToken(current.refreshToken)
        if (refreshedToken != null) {
            current.accessToken = refreshedToken
            authorizationRepository.saveTokens(current)
        }
    }
}
