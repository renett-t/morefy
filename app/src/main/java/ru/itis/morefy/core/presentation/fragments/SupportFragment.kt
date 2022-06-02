package ru.itis.morefy.core.presentation.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.morefy.R
import ru.itis.morefy.databinding.FragmentSupportBinding


class SupportFragment : Fragment(R.layout.fragment_support) {
    private lateinit var binding: FragmentSupportBinding

    private var strMsg: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSupportBinding.bind(view)

        with(binding){
            btnSend.setOnClickListener {
                strMsg = txtMsg.text.toString()
                composeEmail(strMsg)
            }
        }
    }

    fun composeEmail(strMsg: String) {
        val adresses = arrayOf("vzlommamonta@gmail.com")
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, adresses)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Support Morefy App")
        emailIntent.putExtra(Intent.EXTRA_TEXT, strMsg)

        startActivity(Intent.createChooser(emailIntent, "Send mail..."))
        }
    }

