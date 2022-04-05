package ru.itis.morefy.core.data.tokens.local

import android.content.Context
import ru.itis.morefy.core.domain.models.TokenContainer
import ru.itis.morefy.core.domain.repository.AuthorizationRepository

/**
 * Реализация интерфейса на основе Shared Preferences
 */
class AuthorizationRepositoryImpl constructor(
    context: Context
) : AuthorizationRepository  {
    private var sharedPrefsClient: SharedPreferencesClient = SharedPreferencesClient(context)

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

    override fun emptyRepository() {
        sharedPrefsClient.deleteAll()
    }
}
