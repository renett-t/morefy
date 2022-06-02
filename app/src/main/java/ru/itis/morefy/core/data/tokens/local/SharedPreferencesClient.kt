package ru.itis.morefy.core.data.tokens.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import ru.itis.morefy.R
import ru.itis.morefy.core.domain.models.TokenContainer
import javax.inject.Inject

private const val CLIENT_ID = "31f5b8989f5e4b1abc2a0b45e06cd72f"

const val APP_CREDS = "APP_CREDS_KEY"
const val ACCESS_TOKEN = "ACCESS_TOKEN_KEY"
const val TOKEN_TYPE = "TOKEN_TYPE_KEY"
const val SCOPE = "SCOPE_KEY"
const val EXPIRES_IN = "EXPIRES_IN_KEY"
const val REFRESH_TOKEN = "REFRESH_TOKEN_KEY"

class SharedPreferencesClient @Inject constructor(
    private val context: Context
) {
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(
            context.getString(R.string.shared_prefs_auth),
            Context.MODE_PRIVATE
        )
    }

    private var isSaved = false

    fun isCredentialsSaved(): Boolean = isSaved
    fun saveCredentials() {
        val CLIENT_SECRET="311241"
        saveApplicationCredentials(CLIENT_ID, CLIENT_SECRET)
        isSaved = true
    }

    fun saveTokens(tokenContainer: TokenContainer) {
        with(sharedPreferences.edit()) {
            putString(ACCESS_TOKEN, tokenContainer.accessToken)
            putString(TOKEN_TYPE, tokenContainer.tokenType)
            putString(SCOPE, tokenContainer.scope)
            putInt(EXPIRES_IN, tokenContainer.expiresIn)
            putString(REFRESH_TOKEN, tokenContainer.refreshToken)
            apply()
        }
    }

    fun saveNewToken(tokenContainer: TokenContainer) {
        saveTokens(tokenContainer)
    }

    fun getTokens(): TokenContainer {
        val accessToken = sharedPreferences.getString(ACCESS_TOKEN, null)
        val tokenType = sharedPreferences.getString(TOKEN_TYPE, null)
        val scope = sharedPreferences.getString(SCOPE, null)
        val expiresIn = sharedPreferences.getInt(EXPIRES_IN, 3600)
        val refreshToken = sharedPreferences.getString(REFRESH_TOKEN, null)
        if (accessToken != null && tokenType != null && scope != null && expiresIn != null && refreshToken != null)
            return TokenContainer(
                accessToken, tokenType, scope, expiresIn, refreshToken
            )
        else
            throw AuthDataException("No tokens saved")
    }

    fun deleteAll() {
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }

    fun getApplicationCredentials(): String {
        val result = sharedPreferences.getString(APP_CREDS, null)
        return result ?: throw AuthDataException("Problem with retrieving auth data")
    }

    fun getClientId(): String {
        return CLIENT_ID
    }

    private fun saveApplicationCredentials(id: String, secret: String) {
        val byteArray = "${id}:${secret}".toByteArray()
        val result = Base64.encodeToString(byteArray, Base64.NO_WRAP)

        with(sharedPreferences.edit()) {
            putString(APP_CREDS, result)
            apply()
        }
    }

    fun isTokenSaved(): Boolean {
        val token = sharedPreferences.getString(ACCESS_TOKEN, null)
        return token != null
    }
}
