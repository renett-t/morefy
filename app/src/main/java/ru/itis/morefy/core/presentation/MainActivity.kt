package ru.itis.morefy.core.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.itis.morefy.R

class MainActivity : AppCompatActivity() {
    private val isAuthenticated = false // ха-ха всегда редирект на логин-активити

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!isAuthenticated) {
            goToLoginActivity()
        }
    }

    private fun goToLoginActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        // todo: intent flags
    }
}
