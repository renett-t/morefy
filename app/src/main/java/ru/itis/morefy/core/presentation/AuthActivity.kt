package ru.itis.morefy.core.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import ru.itis.morefy.R
import ru.itis.morefy.core.domain.repository.AuthorizationRepository
import ru.itis.morefy.core.domain.repository.SpotifyTokensRepository
import ru.itis.morefy.core.presentation.extensions.appComponent
import ru.itis.morefy.core.presentation.viewmodels.TokensViewModel
import ru.itis.morefy.databinding.ActivityLoginBinding
import javax.inject.Inject

private const val AUTH_CODE_REQUEST_CODE = 0x10

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var authorizationRepository: AuthorizationRepository

    @Inject
    lateinit var spotifyTokensRepository: SpotifyTokensRepository

    @Inject
    lateinit var viewModel: TokensViewModel

    private var isFinishedAuthentication = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        binding = ActivityLoginBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        this.applicationContext.appComponent.inject(this)
        initializeObservers()
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
                    isFinishedAuthentication = true

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
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("token", "successful")
        finishAffinity()
        startActivity(intent)
    }

    private fun requestCodeToAuthenticate() {
        val request = getAuthenticationRequest(AuthorizationResponse.Type.CODE)
        AuthorizationClient.openLoginActivity(this, AUTH_CODE_REQUEST_CODE, request)
    }

    private fun getAccessAndRefreshTokens(code: String) {
        showMessage(getString(R.string.get_access_message))
        viewModel.requestTokensByCode(code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AUTH_CODE_REQUEST_CODE) {
            processGivenResponse(AuthorizationClient.getResponse(resultCode, data))
        }
    }

    private fun processGivenResponse(response: AuthorizationResponse?) {
        when (response?.type) {
            AuthorizationResponse.Type.CODE -> {
                Log.e(
                    "SPOTIFY TOKEN SDK LIB RESULT",
                    "code=${response.code}, state=${response.state} \ntoken=${response.accessToken}, expiresIn=${response.expiresIn}"
                )
                getAccessAndRefreshTokens(response.code)
            }
            AuthorizationResponse.Type.ERROR -> {
                // todo: error handling
                Log.e(
                    "SPOTIFY TOKEN SDK LIB ERROR",
                    "code=${response.code}, state=${response.state} \ntoken=${response.accessToken}, expiresIn=${response.expiresIn}"
                )
                Log.e(
                    "SPOTIFY TOKEN SDK LIB ERROR",
                    "error=${response.error}, state=${response.state}"
                )
            }
            else -> {

            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        Log.e("AUTH ACTIVITY", "GOT RESULT IN INTENT FROM BROWSER")
        val uri = intent?.data
        uri?.let {
            processGivenResponse(AuthorizationResponse.fromUri(it))
        }
    }

    private fun getAuthenticationRequest(type: AuthorizationResponse.Type): AuthorizationRequest {
        val builder = AuthorizationRequest.Builder(
            authorizationRepository.getClientId(), type,
            spotifyTokensRepository.getRedirectUri().toString()
        )

        builder
            .setScopes(resources.getStringArray(R.array.scopes))
            .setShowDialog(true)

        return builder.build()
    }

    override fun onBackPressed() {
        if (!isFinishedAuthentication) {
            Log.e("AUTH ACTIVITY", "back pressed, but auth not finished")
            Log.e("AUTH ACTIVITY", "closing the app")
            // todo: close app
            finishAffinity()
        } else {
            super.onBackPressed()
        }
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
