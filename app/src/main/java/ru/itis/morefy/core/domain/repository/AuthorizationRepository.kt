package ru.itis.morefy.core.domain.repository

import ru.itis.morefy.core.domain.models.TokenContainer

/**
 * Storage of tokens and authorization data.
 */
interface AuthorizationRepository {
    /**
     * Returns the Base64-encrypted string {CLIENT_ID}:{CLIENT_SECRET}
     */
    fun getApplicationCredentials() : String
    fun getClientId() : String

    fun saveTokens(tokenContainer: TokenContainer)
    fun getTokens(): TokenContainer

    /**
     * Removes all tokens data saved locally
     */
    fun emptyRepository()
}
