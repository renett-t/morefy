package ru.itis.morefy.core.data.tokens.local

import android.content.Context
import ru.itis.morefy.core.domain.models.TokenContainer
import ru.itis.morefy.core.domain.repository.AuthorizationRepository
import javax.inject.Inject

/**
 * Реализация интерфейса AuthorizationRepository на основе Shared Preferences
 */
class AuthorizationRepositoryImpl @Inject constructor(
    private val sharedPrefsClient: SharedPreferencesClient
) : AuthorizationRepository {

    override fun getApplicationCredentials(): String {
        return sharedPrefsClient.getApplicationCredentials()
    }

    override fun getClientId(): String {
        return sharedPrefsClient.getClientId()
    }

    override fun saveTokens(tokenContainer: TokenContainer) {
        sharedPrefsClient.saveTokens(tokenContainer)
    }

    override fun getTokens(): TokenContainer {
        return sharedPrefsClient.getTokens()
    }

    override fun isTokenSaved(): Boolean {
        return sharedPrefsClient.isTokenSaved()
    }

    override fun emptyRepository() {
        sharedPrefsClient.deleteAll()
    }

    override fun checkCredentials() {
        if (!sharedPrefsClient.isCredentialsSaved())
            sharedPrefsClient.saveCredentials()
    }
}
