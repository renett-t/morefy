package ru.itis.morefy.statistics.presentation.tabs

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import ru.itis.morefy.R
import ru.itis.morefy.core.presentation.extensions.appComponent
import ru.itis.morefy.databinding.FragmentTopArtistsBinding
import ru.itis.morefy.statistics.di.assisted.TopArtistsAdapterFactory
import ru.itis.morefy.statistics.presentation.rv.TopArtistAdapter
import ru.itis.morefy.statistics.presentation.viewmodel.StatsViewModel
import javax.inject.Inject

class TopArtistsFragment : Fragment(R.layout.fragment_top_artists) {
    private lateinit var binding: FragmentTopArtistsBinding

    @Inject
    lateinit var adapterFactory: TopArtistsAdapterFactory
    lateinit var artistAdapter: TopArtistAdapter

    @Inject
    lateinit var statsViewModel: StatsViewModel

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTopArtistsBinding.bind(view)

        initRecyclerView()
        initObserving()
        getData()
    }

    private fun getData() {
        statsViewModel.getUserTopArtists(
            statsViewModel.getTimeRange(),
            statsViewModel.getAmountToRequest()
        )
    }

    private fun initRecyclerView() {
        artistAdapter = adapterFactory.provideTopArtistsAdapter(
            Glide.with(requireContext())
        ) {
            navigateToTrackScreen(it)
        }

        binding.rvTopArtists.apply {
            adapter = artistAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL))
        }
    }

    private fun initObserving() {
        statsViewModel.topArtists.observe(viewLifecycleOwner) {
            it.fold(
                onSuccess = { tracks ->
                    artistAdapter.submitList(tracks)
                },
                onFailure = { ex ->
                    Log.e("STATS: TopArtistsFragment", "Some problem retrieving top artists. ${ex.message}")
                }
            )
        }
    }

    private fun navigateToTrackScreen(id: String) {
//        val bundle = Bundle().apply {
//            putString("ARTIST_ID", id) // todo: ARTIST_ID to constant of ArtistViewFragment
//        }
//
//        val options = NavOptions.Builder()
//            .setLaunchSingleTop(true) //todo: add animations
//            .build()
//
//        findNavController(R.id.container).navigate(
//            R.id.action_topTracksFragment_to_,
//            bundle,
//            options
//        )
        showMessage("There should be navigation to artist screen")
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            requireActivity().findViewById(R.id.container),
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}
