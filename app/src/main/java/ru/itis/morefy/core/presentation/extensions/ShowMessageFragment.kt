package ru.itis.morefy.core.presentation.extensions

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.itis.morefy.R

fun Fragment.showMessage(message: String) {
    Snackbar.make(
        requireActivity().findViewById(R.id.container),
        message,
        Snackbar.LENGTH_SHORT
    ).show()
}
