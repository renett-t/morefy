package ru.itis.morefy.core.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.media3.common.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.itis.morefy.R
import ru.itis.morefy.core.di.assisted.UserPlaylistAdapterFactory
import ru.itis.morefy.core.domain.models.Playlist
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.core.domain.models.User
import ru.itis.morefy.core.presentation.extensions.appComponent
import ru.itis.morefy.core.presentation.extensions.showMessage
import ru.itis.morefy.core.presentation.rv.UserPlaylistsAdapter
import ru.itis.morefy.core.presentation.viewmodels.HomeViewModel
import ru.itis.morefy.core.presentation.viewmodels.ProfileViewModel
import ru.itis.morefy.databinding.FragmentHomeBinding
import ru.itis.morefy.databinding.FragmentProfileBinding
import javax.inject.Inject

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding


    @Inject
    lateinit var adapterFactory: UserPlaylistAdapterFactory
    lateinit var playlistsAdapter: UserPlaylistsAdapter

    @Inject
    lateinit var viewModel: HomeViewModel

     lateinit var track : Track

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        initRecyclerView()
        initObservers()
        startDownloadingData()
    }

    private fun startDownloadingData() {
        viewModel.getUserData()
        viewModel.getPlaylists()
        viewModel.getUserRecentlyTracks()
    }

    private fun initRecyclerView() {
        with(binding) {
            playlistsAdapter = adapterFactory.provideUserPlaylistsAdapter(
                Glide.with(requireContext())
            ) {
                navigateToPlaylistScreen(it)
            }

            rvPlaylists.apply {
                adapter = playlistsAdapter
                addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL))
            }
        }
    }

    private fun navigateToPlaylistScreen(it: String) {
        //todo
        showMessage("There should be navigation to playlist screen")
    }


    private fun initObservers() {
        viewModel.userData.observe(viewLifecycleOwner) {
            it.fold(
                onSuccess = { user ->
                    updateView(user)
                },
                onFailure = {
                    Log.e("ERROR INSIDE FRAGMENT", "UNABLE TO GET DATA FROM VIEW")
                }
            )
        }

        viewModel.playlists.observe(viewLifecycleOwner) {
            it.fold(
                onSuccess = { playlists ->
                    updateRecyclerAndView(playlists)
                },
                onFailure = {
                    Log.e("ERROR PROFILE FRAGMENT", "UNABLE TO GET DATA FROM VIEW - PLAYLISTS")
                }
            )
        }

        viewModel.tracks.observe(viewLifecycleOwner){
            it.fold(
                onSuccess = {tracks ->
                    track = tracks.elementAt(0)
                    updateTrack(track)
                },
                onFailure = {
                    Log.e("ERROR PROFILE FRAGMENT", "UNABLE TO GET DATA FROM VIEW - PLAYLISTS")
                }
            )
        }
    }

    private fun updateRecyclerAndView(playlists: List<Playlist>) {
        playlistsAdapter.submitList(playlists)
    }

    private fun updateView(user: User) {
        with(binding) {
            Glide.with(requireContext()).load(user.imageUrl).into(ivProfilePicture)
            tvUserName.text = user.name
            println(user)
        }
    }

    private fun updateTrack(tracks: Track){
        with(binding){
            Glide.with(requireContext()).load(tracks.previewUrl).into(ivSongImg)
            tvSongName.text = tracks.name
            tvSongAuthor.text = tracks.name
        }
    }
}
