package ru.itis.morefy.search.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.itis.morefy.R
import ru.itis.morefy.core.di.assisted.ArtistsAdapterFactory
import ru.itis.morefy.core.di.assisted.TracksAdapterFactory
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.core.presentation.extensions.appComponent
import ru.itis.morefy.core.presentation.extensions.findNavigationController
import ru.itis.morefy.core.presentation.extensions.showMessage
import ru.itis.morefy.core.presentation.fragments.details.ARTIST_ID_KEY
import ru.itis.morefy.core.presentation.fragments.details.TRACK_ID_KEY
import ru.itis.morefy.core.presentation.fragments.details.rv.track.ArtistsAdapter
import ru.itis.morefy.core.presentation.rv.TracksAdapter
import ru.itis.morefy.databinding.FragmentSearchBinding
import ru.itis.morefy.search.presentation.viewmodel.SearchViewModel
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {
    private lateinit var binding: FragmentSearchBinding

    @Inject
    lateinit var viewModel: SearchViewModel

    @Inject
    lateinit var artistAdapterFactory: ArtistsAdapterFactory

    @Inject
    lateinit var tracksAdapterFactory: TracksAdapterFactory

    private lateinit var artistsAdapter: ArtistsAdapter
    private lateinit var tracksAdapter: TracksAdapter

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        initRecyclers()
        initObservers()
        initSearchView()
    }

    private fun initSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    showMessage(getString(R.string.downloading_data))
                    searchForItems(query)
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean = false

        })
    }

    private fun searchForItems(query: String) {
        viewModel.getArtistsByQuery(query)
        viewModel.getPopularTracks(query)
    }

    private fun initObservers() {
        viewModel.foundArtists.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = {
                    updateViewWithFoundArtists(it)
                },
                onFailure = {
                    Log.e(
                        "SearchFragment",
                        "Some problem searching for artists by query. ${it.message}"
                    )
                }
            )
        }
        viewModel.foundTracks.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = {
                    updateViewWithFoundTracks(it)
                },
                onFailure = {
                    Log.e(
                        "SearchFragment",
                        "Some problem searching for artists by query. ${it.message}"
                    )
                }
            )
        }
    }

    private fun updateViewWithFoundTracks(list: List<Track>) {
        with(binding) {
            tvFoundArtistsTitle.visibility = View.VISIBLE
            rvFoundArtists.visibility = View.VISIBLE
        }
        tracksAdapter.submitList(list)
    }

    private fun updateViewWithFoundArtists(list: List<Artist>) {
        with(binding) {
            tvFoundTracksTitle.visibility = View.VISIBLE
            rvFoundTracks.visibility = View.VISIBLE
        }
        artistsAdapter.submitList(list)
    }

    private fun initRecyclers() {
        tracksAdapter = tracksAdapterFactory.provideTracksAdapter(
            Glide.with(requireContext())
        ) {
            navigateToTrackScreen(it)
        }

        binding.rvFoundTracks.apply {
            adapter = tracksAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL))
        }

        artistsAdapter = artistAdapterFactory.provideArtistsAdapter(
            Glide.with(requireContext())
        ) {
            navigateToArtistScreen(it)
        }

        binding.rvFoundTracks.apply {
            adapter = tracksAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL))
        }
    }

    private fun navigateToArtistScreen(id: String) {
        val bundle = Bundle().apply {
            putString(ARTIST_ID_KEY, id)
        }

        val options = NavOptions.Builder()
            .setLaunchSingleTop(true) // todo: add animations
            .build()

        requireActivity().findNavigationController(R.id.container).navigate(
            R.id.action_searchFragment_to_artistFragment,
            bundle,
            options
        )
    }

    private fun navigateToTrackScreen(id: String) {
        val bundle = Bundle().apply {
            putString(TRACK_ID_KEY, id)
        }

        val options = NavOptions.Builder()
            .setLaunchSingleTop(true) // todo: add animations
            .build()

        requireActivity().findNavigationController(R.id.container).navigate(
            R.id.action_searchFragment_to_trackFragment,
            bundle,
            options
        )
    }

}
