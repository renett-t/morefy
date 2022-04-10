package ru.itis.morefy.core.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.itis.morefy.R
import ru.itis.morefy.core.data.tokens.local.AuthorizationRepositoryImpl
import ru.itis.morefy.core.domain.service.RefreshTokenService

class MainActivity : AppCompatActivity() {
    private lateinit var refreshTokenService: RefreshTokenService

    override fun onCreate(savedInstanceState: Bundle?) {
        // todo: DI injection and return Singleton
        refreshTokenService = RefreshTokenService(AuthorizationRepositoryImpl(applicationContext))

        if (!refreshTokenService.isAuthenticated()) {
            goToLoginActivity()
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun goToLoginActivity() {
        val intent = Intent(this, AuthActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }
}
