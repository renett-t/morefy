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
import ru.itis.morefy.databinding.ActivityLoginBinding

const val AUTH_TOKEN_REQUEST_CODE = 0x10
const val REDIRECT_URI = "ru.itis.morefy://login"
const val CLIENT_ID = "fc313a64d9604eeb907c8b14ab14afb6"

class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    var accessToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        binding = ActivityLoginBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        binding.btnAuth.setOnClickListener {
            requestLogin()
        }
    }

    private fun requestLogin() {
        val request = getAuthenticationRequest(AuthorizationResponse.Type.TOKEN)
        AuthorizationClient.openLoginActivity(this, AUTH_TOKEN_REQUEST_CODE, request)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AUTH_TOKEN_REQUEST_CODE) {
            val response = AuthorizationClient.getResponse(resultCode, data)
            when (response.type) {
                AuthorizationResponse.Type.TOKEN -> {
                    Log.i("TOKEN-RESULT","code=${response.code}, state=${response.state} \ntoken=${response.accessToken}, expiresIn=${response.expiresIn}")
                    accessToken = response.accessToken
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
        val builder = AuthorizationRequest.Builder(CLIENT_ID, type,
            Uri.parse(REDIRECT_URI).toString()
        )

        builder.setScopes(arrayOf("user-read-playback-state" , "user-read-recently-played",
                "user-read-private", "user-read-email", "user-follow-read", "user-library-modify",
                "user-library-read", "app-remote-control", "user-top-read", "user-read-recently-played",
                "playlist-modify-private", "playlist-read-collaborative", "playlist-read-private",
                "playlist-modify-public"))

        builder.setShowDialog(true) // todo: is it needed?

        return builder.build()
    }
}
