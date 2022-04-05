package ru.itis.morefy.core.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import ru.itis.morefy.core.data.tokens.local.AuthorizationRepositoryImpl
import ru.itis.morefy.core.data.tokens.SpotifyTokensRepositoryImpl
import ru.itis.morefy.core.data.tokens.net.response.TokenResponseMapper
import ru.itis.morefy.core.domain.models.TokenContainer
import ru.itis.morefy.core.domain.repository.AuthorizationRepository
import ru.itis.morefy.core.domain.repository.SpotifyTokensRepository
import ru.itis.morefy.databinding.ActivityLoginBinding

const val AUTH_CODE_REQUEST_CODE = 0x10

class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private var code: String? = null
    var tokenContainer: TokenContainer? = null

    lateinit var spotifyTokensRepository: SpotifyTokensRepository
    lateinit var authorizationRepository: AuthorizationRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        initializeServices()

        binding = ActivityLoginBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        binding.btnAuth.setOnClickListener {
            requestCodeToAuthenticate()
        }
    }

    // todo: change to DI injection, maybe спрятать это за одним сервисом, который будет получать, сохранять и обновлять токены
    private fun initializeServices() {
        spotifyTokensRepository = SpotifyTokensRepositoryImpl(applicationContext, TokenResponseMapper())
        authorizationRepository = AuthorizationRepositoryImpl(applicationContext)
    }

    private fun requestCodeToAuthenticate() {
        val request = getAuthenticationRequest(AuthorizationResponse.Type.CODE)
        AuthorizationClient.openLoginActivity(this, AUTH_CODE_REQUEST_CODE, request)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AUTH_CODE_REQUEST_CODE) {
            val response = AuthorizationClient.getResponse(resultCode, data)
            when (response.type) {
                AuthorizationResponse.Type.CODE -> {
                    Log.e("TOKEN-RESULT","code=${response.code}, state=${response.state} \ntoken=${response.accessToken}, expiresIn=${response.expiresIn}")
                    code = response.code
                    getAccessAndRefreshTokens()
                    // todo: save token to db, redirect to main activity
                }
                AuthorizationResponse.Type.ERROR -> {
                    // todo: error handling
                    Log.e("TOKEN-ERROR","code=${response.code}, state=${response.state} \ntoken=${response.accessToken}, expiresIn=${response.expiresIn}")
                    Log.e("TOKEN-ERROR", "error=${response.error}, state=${response.state}")
                }
                else -> {
                    // todo: repeat request maybe?
                    Log.e("AUTH-WRONG", "${response.type}")
                    Log.e("AUTH-WRONG","code=${response.code}, state=${response.state} \ntoken=${response.accessToken}, expiresIn=${response.expiresIn}")
                }
            }
        }
    }

    private fun getAuthenticationRequest(type: AuthorizationResponse.Type): AuthorizationRequest {
        val builder = AuthorizationRequest.Builder(authorizationRepository.getClientId(), type,
            Uri.parse(spotifyTokensRepository.getRedirectUri()).toString()
        )

        builder.setScopes(arrayOf("user-read-playback-state" , "user-read-recently-played",
                "user-read-private", "user-read-email", "user-follow-read", "user-library-modify",
                "user-library-read", "app-remote-control", "user-top-read", "user-read-recently-played",
                "playlist-modify-private", "playlist-read-collaborative", "playlist-read-private",
                "playlist-modify-public"))

        builder.setShowDialog(true) // todo: is it needed?

        return builder.build()
    }

    private fun getAccessAndRefreshTokens() {
        Log.d("REFRESH TOKENS", "ABOUT TO MAKE REQUEST")

        code?.let {
            tokenContainer = spotifyTokensRepository.getTokensByCode(it)
            tokenContainer?.let { it1 -> authorizationRepository.saveTokens(it1) }
        }

        Log.d("REFRESH TOKENS", "ПОЛУЧЕНО")
    }

}
