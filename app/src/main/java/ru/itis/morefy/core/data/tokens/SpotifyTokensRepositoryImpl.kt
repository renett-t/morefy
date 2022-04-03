package ru.itis.morefy.core.data.tokens

import android.util.Base64
import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import ru.itis.morefy.core.domain.models.TokenContainer
import ru.itis.morefy.core.domain.repository.SpotifyTokensRepository
import ru.itis.morefy.core.presentation.CLIENT_ID
import java.io.IOException

class SpotifyTokensRepositoryImpl: SpotifyTokensRepository {
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val CLIENT_SECRET = ""
    private val okHttpClient = OkHttpClient()

    override fun getRefreshedAccessToken(refreshToken: String): String {
        TODO("Not yet implemented")
    }

    override fun getTokensByCode(code: String, redirectUrl: String): TokenContainer? {
        val request = Request.Builder()
            .url("https://accounts.spotify.com/api/token")
            .post(getPostBody(code, redirectUrl))
            .addHeader("Authorization", "Basic ${getAuthCredentialsEncoded("${CLIENT_ID}:${CLIENT_SECRET}")}")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .build()

        okHttpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Log.e("ERROR GET CREDS", "code= ${response.code}, body=${response.body?.string()}")
                throw IOException("unable to get credentials")
            } else {
                val body = response.body?.string()
                Log.e("REFRESH TOKENS", "result: $body" )

                val moshi: Moshi = Moshi.Builder().build()
                val jsonAdapter: JsonAdapter<TokenContainer> = moshi.adapter(TokenContainer::class.java)

                return if (body != null) {
                    jsonAdapter.fromJson(body)
                } else {
                    null
                }
            }
        }
    }

    private fun getAuthCredentialsEncoded(credentials: String): String {
        val byteArray = credentials.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }

    private fun getPostBody(code: String, redirectUrl: String?): RequestBody {
        val body = "grant_type=authorization_code&code=${code}&redirect_uri=${redirectUrl}"
        return body.toRequestBody("application/x-www-form-urlencoded".toMediaType())
    }

}
