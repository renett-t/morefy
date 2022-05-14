package ru.itis.morefy.core.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.itis.morefy.R
import ru.itis.morefy.core.domain.service.RefreshTokenService
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.itis.morefy.core.presentation.extensions.appComponent
import ru.itis.morefy.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    @Inject
    lateinit var refreshTokenService: RefreshTokenService

    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        this.applicationContext.appComponent.inject(this)

        if (!refreshTokenService.isAuthenticated()) {
            goToLoginActivity()
        }

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        findBottomNavigation()
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    private fun findBottomNavigation() {
        controller = findController(R.id.container)
        binding.bottomNav.setupWithNavController(controller)
    }

    override fun onBackPressed() {
        when (supportFragmentManager.backStackEntryCount) {
            0 -> super.onBackPressed()
            else -> supportFragmentManager.popBackStack()
        }
    }

    private fun AppCompatActivity.findController(id: Int): NavController {
        return (supportFragmentManager.findFragmentById(id) as NavHostFragment).navController
    }

    private fun goToLoginActivity() {
        val intent = Intent(this, AuthActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }
}
