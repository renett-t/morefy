package ru.itis.morefy.core.domain.service

import ru.itis.morefy.core.domain.models.TokenContainer
import ru.itis.morefy.core.domain.repository.AuthorizationRepository

class RefreshTokenService(
    private val authorizationRepository: AuthorizationRepository,
) {

    fun isAuthenticated(): Boolean {
        return authorizationRepository.isTokenSaved()
    }

    fun getTokens(): TokenContainer {
        return authorizationRepository.getTokens()
    }
}
