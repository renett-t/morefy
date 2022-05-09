package ru.itis.morefy.core.data.tokens.net

import android.content.Context
import android.util.Log
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import ru.itis.morefy.core.data.tokens.local.AuthorizationRepositoryImpl
import ru.itis.morefy.core.data.tokens.net.response.SpotifyTokensResponse
import ru.itis.morefy.core.data.tokens.net.response.SpotifyTokenResponseMapper
import ru.itis.morefy.core.domain.models.TokenContainer
import ru.itis.morefy.core.domain.repository.SpotifyTokensRepository
import java.io.IOException

private const val CONTENT_TYPE = "application/x-www-form-urlencoded"
private const val URL = "https://accounts.spotify.com/api/token"
private const val REDIRECT_URI = "ru.itis.morefy://login"

// ref: https://developer.spotify.com/documentation/general/guides/authorization/code-flow/
class SpotifyTokensRepositoryImpl constructor(
    private val context: Context,
    private val tokenResponseMapper: SpotifyTokenResponseMapper
): SpotifyTokensRepository {

    private val authorizationRepository = AuthorizationRepositoryImpl(context) // todo: di container injection
    private val okHttpClient = OkHttpClient()

    // todo: test method
    override suspend fun getRefreshedAccessToken(refreshToken: String): String? {
        authorizationRepository.checkCredentials()

        val request = generateRequestToSpotify(getPostBodyForRefreshToken(refreshToken))

        okHttpClient.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                val body = response.body?.string()
                Log.e("SUCCESS REFRESH TOKENS", "result: $body")

                return if (body != null) {
                    Json.decodeFromString<SpotifyTokensResponse>(body).let {
                        tokenResponseMapper.map(it).refreshToken
                    }
                } else null
            } else {
                Log.e("ERROR REQUESTING REFRESH TOKEN", "code= ${response.code}, body=${response.body?.string()}")
                throw IOException("Unable to get refresh token")            }
        }
    }

    override suspend fun getTokensByCode(code: String): TokenContainer? {
        authorizationRepository.checkCredentials()
        val request = generateRequestToSpotify(getPostBodyForAccessToken(code, REDIRECT_URI))

        okHttpClient.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                val body = response.body?.string()
                Log.e("SUCCESS ACCESS TOKENS", "result: $body")

                return if (body != null) {
                    Json.decodeFromString<SpotifyTokensResponse>(body).let {
                        tokenResponseMapper.map(it)
                    }
                } else null
            } else {
                Log.e("ERROR REQUESTING ACCESS TOKEN", "code= ${response.code}, body=${response.body?.string()}")
                throw IOException("Unable to get credentials")            }
        }
    }

    private fun generateRequestToSpotify(requestBody: RequestBody): Request {
        return Request.Builder()
            .url(URL)
            .post(requestBody)
            .addHeader("Authorization", "Basic ${authorizationRepository.getApplicationCredentials()}")
            .addHeader("Content-Type", CONTENT_TYPE)
            .build()
    }

    override fun getRedirectUri(): String {
        return REDIRECT_URI
    }

    private fun getPostBodyForRefreshToken(refreshToken: String): RequestBody {
        val body = "grant_type=refresh_token&refresh_token=${refreshToken}"
        return encodeToPostBody(body)
    }

    private fun getPostBodyForAccessToken(code: String, redirectUrl: String?): RequestBody {
        val body = "grant_type=authorization_code&code=${code}&redirect_uri=${redirectUrl}"
        return encodeToPostBody(body)
    }

    private fun encodeToPostBody(body: String) = body.toRequestBody(CONTENT_TYPE.toMediaType())
}
