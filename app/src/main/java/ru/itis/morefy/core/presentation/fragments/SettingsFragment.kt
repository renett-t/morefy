package ru.itis.morefy.core.presentation.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import ru.itis.morefy.R
import ru.itis.morefy.core.domain.service.RefreshTokenService
import ru.itis.morefy.core.presentation.AuthActivity
import ru.itis.morefy.core.presentation.extensions.appComponent
import ru.itis.morefy.databinding.FragmentSettingsBinding
import javax.inject.Inject

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private lateinit var binding: FragmentSettingsBinding

    @Inject
    lateinit var tokenService: RefreshTokenService

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        initBtnListeners()
    }

    private fun initBtnListeners() {
        with(binding) {
            btnSignout.setOnClickListener {
                showLogOutDialog()
            }

            btnAboutus.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace<AboutUsFragment>(R.id.container)
                    .addToBackStack(null)
                    .commit()
            }

            btnSupport.setOnClickListener {

            }
        }
    }

    private fun showLogOutDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_logout, null)

        AlertDialog.Builder(context)
            .setView(dialogView)
            .setPositiveButton(R.string.logout) { _, _ ->
                clearCredentialsData()
                goToLoginActivity()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun clearCredentialsData() {
        tokenService.clearData()
    }

    private fun goToLoginActivity() {
        val intent = Intent(context, AuthActivity::class.java)
        activity?.finishAffinity()
        startActivity(intent)
    }
}
