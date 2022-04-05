package ru.itis.morefy.core.data.tokens.net

import android.content.Context
import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import ru.itis.morefy.core.data.tokens.local.AuthorizationRepositoryImpl
import ru.itis.morefy.core.data.tokens.net.response.SpotifyTokensResponse
import ru.itis.morefy.core.data.tokens.net.response.TokenResponseMapper
import ru.itis.morefy.core.domain.models.TokenContainer
import ru.itis.morefy.core.domain.repository.SpotifyTokensRepository
import java.io.IOException

const val CONTENT_TYPE = "application/x-www-form-urlencoded"
const val URL = "https://accounts.spotify.com/api/token"
private val REDIRECT_URI = "ru.itis.morefy://login"

// todo: maybe use retrofit api here???
// ref: https://developer.spotify.com/documentation/general/guides/authorization/code-flow/
class SpotifyTokensRepositoryImpl constructor(
    private val context: Context,
    private val tokenResponseMapper: TokenResponseMapper
): SpotifyTokensRepository {

    private val authorizationRepository = AuthorizationRepositoryImpl(context) // todo: di container injection
    private val okHttpClient = OkHttpClient()

    override fun getRefreshedAccessToken(refreshToken: String): String {
        TODO("Not yet implemented")
    }

    override fun getTokensByCode(code: String): TokenContainer? {
        val request = Request.Builder()
            .url(URL)
            .post(getPostBody(code, REDIRECT_URI))
            .addHeader("Authorization", "Basic ${authorizationRepository.getApplicationCredentials()}")
            .addHeader("Content-Type", CONTENT_TYPE)
            .build()

        Log.e("AUTHH", "SENDING REQUEST TO GET TOKENS")
        okHttpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Log.e("ERROR GETTING CREDS", "code= ${response.code}, body=${response.body?.string()}")
                throw IOException("unable to get credentials")
            } else {
                val body = response.body?.string()
                Log.e("REFRESH TOKENS", "result: $body" )

                val moshi: Moshi = Moshi.Builder().build()
                val jsonAdapter: JsonAdapter<SpotifyTokensResponse> = moshi.adapter(SpotifyTokensResponse::class.java)

                return if (body != null) {
                    jsonAdapter.fromJson(body)?.let {
                        tokenResponseMapper.map(it)
                    }
                } else {
                    null
                }
            }
        }
    }

    override fun getRedirectUri(): String {
        return REDIRECT_URI
    }

    private fun getPostBody(code: String, redirectUrl: String?): RequestBody {
        val body = "grant_type=authorization_code&code=${code}&redirect_uri=${redirectUrl}"
        return body.toRequestBody("application/x-www-form-urlencoded".toMediaType())
    }

}
