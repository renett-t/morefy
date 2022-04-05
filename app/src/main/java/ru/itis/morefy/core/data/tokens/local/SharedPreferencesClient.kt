package ru.itis.morefy.core.data.tokens.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import ru.itis.morefy.R
import ru.itis.morefy.core.domain.models.TokenContainer

private const val CLIENT_ID = "fc313a64d9604eeb907c8b14ab14afb6"

const val APP_CREDS = "APP_CREDS_KEY"
const val ACCESS_TOKEN = "ACCESS_TOKEN_KEY"
const val TOKEN_TYPE = "TOKEN_TYPE_KEY"
const val SCOPE = "SCOPE_KEY"
const val EXPIRES_IN = "EXPIRES_IN_KEY"
const val REFRESH_TOKEN = "REFRESH_TOKEN_KEY"
class SharedPreferencesClient(
    context: Context
) {
    init {
        val CLIENT_SECRET = "*** *****"
        saveApplicationCredentials(CLIENT_ID, CLIENT_SECRET)
    }

    private val sharedPreferences: SharedPreferences
    init {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_prefs_auth), Context.MODE_PRIVATE)
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
        TODO()
    }

    fun getTokens() : TokenContainer {
        TODO()
    }

    fun deleteAll() {

    }
    fun getApplicationCredentials(): String {
        val result = sharedPreferences.getString(APP_CREDS, null)
        return result ?: throw AuthDataException("Problem with retrieving auth data")
    }

    fun getClientId(): String {
        return CLIENT_ID
    }

    private fun saveApplicationCredentials(id: String, secret: String) {
        val byteArrayClientId = id.toByteArray()
        val byteArrayClientSecret = secret.toByteArray()
        val result = "${Base64.encodeToString(byteArrayClientId, Base64.NO_WRAP)}:${Base64.encodeToString(byteArrayClientSecret, Base64.NO_WRAP)}"
        with(sharedPreferences.edit()) {
            putString(APP_CREDS, result)
            apply()
        }
    }

}
