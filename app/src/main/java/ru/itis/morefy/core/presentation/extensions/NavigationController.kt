package ru.itis.morefy.core.presentation.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun AppCompatActivity.findNavigationController(id: Int): NavController {
    return (supportFragmentManager.findFragmentById(id) as NavHostFragment).navController
}

fun FragmentActivity.findNavigationController(id: Int): NavController {
    return (supportFragmentManager.findFragmentById(id) as NavHostFragment).navController
}
