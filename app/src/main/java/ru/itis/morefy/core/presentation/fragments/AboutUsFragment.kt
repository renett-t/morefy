package ru.itis.morefy.core.presentation.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.media3.common.util.Log
import com.bumptech.glide.Glide
import ru.itis.morefy.R
import ru.itis.morefy.core.domain.models.User
import ru.itis.morefy.core.presentation.extensions.appComponent
import ru.itis.morefy.core.presentation.extensions.showMessage
import ru.itis.morefy.core.presentation.viewmodels.AboutUsViewModel
import ru.itis.morefy.databinding.FragmentAboutUsBinding
import javax.inject.Inject


class AboutUsFragment : Fragment(R.layout.fragment_about_us) {
    private lateinit var binding: FragmentAboutUsBinding

    @Inject
    lateinit var viewModel: AboutUsViewModel

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAboutUsBinding.bind(view)

        initObserver()
        startDownloadingData("n5kkhipf2kh5pq22f4eg80pqt")
        startDownloadingData("31uukgzsjhqvh25w6hiztzbjtmhe")

        navigateToSpotify()
    }

    private fun startDownloadingData(UserId:String) {
        viewModel.getUserData(UserId)
    }


    private fun navigateToSpotify() {
        with(binding){
            ivProfilePictureFirst.setOnClickListener{
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://open.spotify.com/user/n5kkhipf2kh5pq22f4eg80pqt"))
                startActivity(browserIntent)
            }
            ivProfilePictureSecond.setOnClickListener{
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://open.spotify.com/user/31uukgzsjhqvh25w6hiztzbjtmhe"))
                startActivity(browserIntent)
            }
        }
    }


    private fun initObserver() {

        viewModel.userData.observe(viewLifecycleOwner) {
            it.fold(
                onSuccess = { user ->
                    updateViewFirstUser(user)
                },
                onFailure = {
                    Log.e("ERROR ABOUT US FRAGMENT", "UNABLE TO GET DATA FROM VIEW")
                }
            )
        }

        viewModel.userData.observe(viewLifecycleOwner) {
            it.fold(
                onSuccess = { user ->
                    updateViewSecondUser(user)
                },
                onFailure = {
                    Log.e("ERROR ABOUT US FRAGMENT", "UNABLE TO GET DATA FROM VIEW")
                }
            )
        }
    }

    private fun updateViewFirstUser(user: User) {
        with(binding) {
            Glide.with(requireContext()).load(user.imageUrl).into(ivProfilePictureFirst)
            tvUserNameFirst.text = user.name
            println(user)
        }
    }

    private fun updateViewSecondUser(user: User) {
        with(binding) {
            Glide.with(requireContext()).load(user.imageUrl).into(ivProfilePictureSecond)
            tvUserNameSecond.text = user.name
            println(user)
        }
    }

}
