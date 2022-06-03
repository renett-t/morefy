package ru.itis.morefy.core.presentation.fragments.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.navigation.NavOptions
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import ru.itis.morefy.R
import ru.itis.morefy.core.di.assisted.GenresAdapterFactory
import ru.itis.morefy.core.di.assisted.ArtistsCardsAdapterFactory
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.presentation.extensions.appComponent
import ru.itis.morefy.core.presentation.extensions.findNavigationController
import ru.itis.morefy.core.presentation.extensions.showMessage
import ru.itis.morefy.core.presentation.fragments.SupportFragment
import ru.itis.morefy.core.presentation.fragments.details.rv.artist.ArtistsCardsAdapter
import ru.itis.morefy.core.presentation.fragments.details.rv.artist.GenresAdapter
import ru.itis.morefy.core.presentation.viewmodels.details.ArtistViewModel
import ru.itis.morefy.databinding.FragmentArtistBinding
import ru.itis.morefy.statistics.di.assisted.TopTracksAdapterFactory
import ru.itis.morefy.statistics.presentation.rv.TopTracksAdapter
import javax.inject.Inject


const val ARTIST_ID_KEY: String = "ARTIST_ID_KEY"

class ArtistFragment : Fragment(R.layout.fragment_artist) {
    private lateinit var binding: FragmentArtistBinding

    @Inject
    lateinit var viewModel: ArtistViewModel

    @Inject
    lateinit var artistsCardsAdapterFactory: ArtistsCardsAdapterFactory

    @Inject
    lateinit var topTracksAdapterFactory: TopTracksAdapterFactory

    @Inject
    lateinit var genresAdapterFactory: GenresAdapterFactory

    private lateinit var imageDownloader: RequestManager

    private lateinit var artistsAdapter: ArtistsCardsAdapter
    private lateinit var genresAdapter: GenresAdapter
    private lateinit var popularTracksAdapter: TopTracksAdapter

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArtistBinding.bind(view)

        initRecyclers()
        initObservers()
        arguments?.getString(ARTIST_ID_KEY)?.let {
            startDownloadingData(it)
        }
    }

    private fun initRecyclers() {
        imageDownloader = Glide.with(requireContext())
        artistsAdapter = artistsCardsAdapterFactory.provideArtistsCardsAdapter(
            Glide.with(requireContext())
        ) {
            navigateToArtistScreen(it)
        }
        popularTracksAdapter = topTracksAdapterFactory.provideTopTracksAdapter(
            Glide.with(requireContext())
        ) {
            navigateToTrackScreen(it)
        }
        genresAdapter = genresAdapterFactory.provideGenresAdapter {
            // nothing
        }

        with(binding) {
            rvPopularTracks.apply {
                addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL))
                adapter = popularTracksAdapter
            }
            rvTopGenres.apply {
                addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL))
                adapter = genresAdapter
            }
            rvSimilarArtists.apply {
                addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL))
                adapter = artistsAdapter
            }
        }
    }

    private fun initObservers() {
        viewModel.artist.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = {
                    updateView(it)
                },
                onFailure = {
                    Log.e("ERROR ARTIST FRAGMENT", "UNABLE TO GET BASIC ARTIST DATA FROM VIEW MODEL")
                })
        }
        viewModel.similarArtists.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = {
                    artistsAdapter.submitList(it)
                },
                onFailure = {
                    Log.e("ERROR ARTIST FRAGMENT", "UNABLE TO GET SIMILAR ARTISTS DATA FROM VIEW MODEL")
                })
        }
        viewModel.popularTracks.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = {
                    popularTracksAdapter.submitList(it)
                },
                onFailure = {
                    Log.e("ERROR ARTIST FRAGMENT", "UNABLE TO GET ARTIST POPULAR TRACKS DATA FROM VIEW MODEL")
                })
        }
    }

    private fun updateView(artist: Artist) {
        with(binding) {
            imageDownloader.load(artist.imageUrl).into(ivArtistCover)
            tvArtist.text = artist.name
            genresAdapter.submitList(artist.genres)
            tvPopularity.text = artist.popularity.toString()
            btnOpenInSpotifyApp.setOnClickListener {
                openOfficialApp(artist.uri)
            }
            tvSubscriptionsCount.text = artist.followerCount.toString()
        }
    }

    private fun openOfficialApp(uri: String) {
        val launcher = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(launcher)
    }

    private fun startDownloadingData(id: String) {
        viewModel.getArtistInfo(id)
        viewModel.getSimilarArtists(id)
        viewModel.getPopularTracks(id)
    }

    private fun navigateToArtistScreen(id: String) {
        showMessage("Navigation to new artist screen")
        val bundle = Bundle().apply {
            putString(ARTIST_ID_KEY, id)
        }

        parentFragmentManager.beginTransaction()
            .replace<ArtistFragment>(R.id.container)
            .apply {
                arguments = bundle
            }
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToTrackScreen(id: String) {
        val bundle = Bundle().apply {
            putString(TRACK_ID_KEY, id)
        }

        val options = NavOptions.Builder()
            .setLaunchSingleTop(true) // todo: add animations
            .build()

        requireActivity().findNavigationController(R.id.container).navigate(
            R.id.action_artistFragment_to_trackFragment,
            bundle,
            options
        )
    }
}
