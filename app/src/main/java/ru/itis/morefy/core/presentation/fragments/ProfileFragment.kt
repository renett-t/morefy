package ru.itis.morefy.core.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.media3.common.util.Log
import com.bumptech.glide.Glide
import ru.itis.morefy.R
import ru.itis.morefy.core.domain.models.Playlist
import ru.itis.morefy.core.domain.models.User
import ru.itis.morefy.core.presentation.extensions.appComponent
import ru.itis.morefy.core.presentation.viewmodels.ProfileViewModel
import ru.itis.morefy.databinding.FragmentProfileBinding
import javax.inject.Inject

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var viewModel: ProfileViewModel

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfileBinding.bind(view)

        initRecyclerView()
        initObservers()
        startDownloadingData()
    }

    private fun initRecyclerView() {
        TODO("Not yet implemented")
    }

    private fun startDownloadingData() {
        viewModel.getUserData()
        viewModel.getPlaylists()
        viewModel.getFollowedArtistsCount()
    }


    private fun initObservers() {
        viewModel.userData.observe(viewLifecycleOwner) {
            it.fold(
                onSuccess = { user ->
                    updateBaseDataView(user)
                },
                onFailure = {
                    Log.e("ERROR INSIDE FRAGMENT", "UNABLE TO GET DATA FROM VIEW - USER DATA")
                }
            )
        }

        viewModel.playlists.observe(viewLifecycleOwner) {
            it.fold(
                onSuccess = { playlists ->
                    updateRecyclerAndView(playlists)
                },
                onFailure = {
                    Log.e("ERROR INSIDE FRAGMENT", "UNABLE TO GET DATA FROM VIEW - PLAYLISTS")
                }
            )
        }

        viewModel.artistsCount.observe(viewLifecycleOwner) {
            it.fold(
                onSuccess = { artistsCount ->
                    updateArtistsCountView(artistsCount)
                },
                onFailure = {
                    Log.e("ERROR INSIDE FRAGMENT", "UNABLE TO GET DATA FROM VIEW - ARTISTS COUNT")
                }
            )
        }
    }

    private fun updateArtistsCountView(artistsCount: Int) {
        with(binding) {
            tvSubscriptionCount.text = artistsCount.toString()
        }
    }

    private fun updateRecyclerAndView(playlists: List<Playlist>) {
        TODO("Not yet implemented")
    }

    private fun updateBaseDataView(user: User) {
        with(binding) {
            Glide.with(requireContext()).load(user.imageUrl).into(ivProfilePicture)
            tvUserName.text = user.name
            tvSubscriberCount.text = user.followerCount.toString()
        }
    }
}
