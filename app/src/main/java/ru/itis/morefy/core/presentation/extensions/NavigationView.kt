package ru.itis.morefy.core.presentation.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.itis.morefy.R

fun AppCompatActivity.getNavigationBar(): BottomNavigationView {
    return findViewById(R.id.bottom_nav)
}

fun FragmentActivity.getNavigationBar(): BottomNavigationView {
    return findViewById(R.id.bottom_nav)
}
