package ru.itis.morefy.core.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.itis.morefy.R
import ru.itis.morefy.core.data.tokens.local.AuthorizationRepositoryImpl
import ru.itis.morefy.core.domain.service.RefreshTokenService
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.itis.morefy.R
import ru.itis.morefy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var refreshTokenService: RefreshTokenService
    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        // todo: DI injection and return Singleton
        refreshTokenService = RefreshTokenService(AuthorizationRepositoryImpl(applicationContext))

        if (!refreshTokenService.isAuthenticated()) {
            goToLoginActivity()
        }

        super.onCreate(savedInstanceState)

        findBottomNavigation()
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    fun findBottomNavigation() {
        controller = findController(R.id.container)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setupWithNavController(controller)
    }

    override fun onBackPressed() {
        when(supportFragmentManager.backStackEntryCount){
            0 -> super.onBackPressed()
            else -> supportFragmentManager.popBackStack()
        }
    }

    fun AppCompatActivity.findController (id: Int) : NavController {
        return (supportFragmentManager.findFragmentById(id) as NavHostFragment).navController
    }

    private fun goToLoginActivity() {
        val intent = Intent(this, AuthActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }
}
