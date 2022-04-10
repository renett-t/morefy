package ru.itis.morefy.core.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import ru.itis.morefy.R
import ru.itis.morefy.core.data.tokens.local.AuthorizationRepositoryImpl
import ru.itis.morefy.core.data.tokens.net.SpotifyTokensRepositoryImpl
import ru.itis.morefy.core.data.tokens.net.response.SpotifyTokenResponseMapper
import ru.itis.morefy.core.domain.repository.AuthorizationRepository
import ru.itis.morefy.core.domain.repository.SpotifyTokensRepository
import ru.itis.morefy.core.presentation.viewmodels.TokensViewModel
import ru.itis.morefy.core.presentation.viewmodels.ViewModelFactory
import ru.itis.morefy.databinding.ActivityLoginBinding

private const val AUTH_CODE_REQUEST_CODE = 0x10

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var authorizationRepository: AuthorizationRepository
    private lateinit var spotifyTokensRepository: SpotifyTokensRepository
    private lateinit var viewModel: TokensViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        binding = ActivityLoginBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        initializeServices()
        initializeObservers()
    }

    // todo: change to DI injection
    private fun initializeServices() {
        spotifyTokensRepository = SpotifyTokensRepositoryImpl(applicationContext, SpotifyTokenResponseMapper())
        authorizationRepository = AuthorizationRepositoryImpl(applicationContext)

        val factory = ViewModelFactory(spotifyTokensRepository)
        viewModel = ViewModelProvider(this, factory)[TokensViewModel::class.java]
    }

    private fun initializeObservers() {
        binding.btnAuth.setOnClickListener {
            requestCodeToAuthenticate()
        }

        viewModel.tokenContainer.observe(this) {
            it?.fold(
                onSuccess = { tokenContainer ->
                    Log.e("REFRESH TOKENS ПОЛУЧЕНЫ", "SAVING TOKEN YAY = $tokenContainer")
                        authorizationRepository.saveTokens(tokenContainer)

                    val token = authorizationRepository.getTokens()
                    Log.e("SAVED IN PREFS", token.toString())
                    redirectToMainActivity()
                },
                onFailure = {
                    showMessage(getString(R.string.error_getting_tocken_message))
                }
            )
        }
    }

    private fun redirectToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.putExtra("token", "successful")
        startActivity(intent)
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
                    Log.e("SPOTIFY TOKEN SDK LIB RESULT","code=${response.code}, state=${response.state} \ntoken=${response.accessToken}, expiresIn=${response.expiresIn}")
                    getAccessAndRefreshTokens(response.code)
                }
                AuthorizationResponse.Type.ERROR -> {
                    // todo: error handling
                    Log.e("SPOTIFY TOKEN SDK LIB ERROR","code=${response.code}, state=${response.state} \ntoken=${response.accessToken}, expiresIn=${response.expiresIn}")
                    Log.e("SPOTIFY TOKEN SDK LIB ERROR", "error=${response.error}, state=${response.state}")
                }
                else -> {

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

    private fun getAccessAndRefreshTokens(code: String) {
        viewModel.requestTokensByCode(code)
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
