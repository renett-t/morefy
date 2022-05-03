package ru.itis.morefy.core.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.itis.morefy.R
import ru.itis.morefy.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
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
}
