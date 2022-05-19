package ru.itis.morefy.statistics.presentation.tabs

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import ru.itis.morefy.R
import ru.itis.morefy.core.presentation.extensions.appComponent
import ru.itis.morefy.databinding.FragmentTopTracksBinding
import ru.itis.morefy.statistics.di.assisted.TopTracksAdapterFactory
import ru.itis.morefy.statistics.presentation.rv.TopTracksAdapter
import ru.itis.morefy.statistics.presentation.viewmodel.StatsViewModel
import javax.inject.Inject

class TopTracksFragment: Fragment(R.layout.fragment_top_tracks)  {
    private lateinit var binding: FragmentTopTracksBinding
    @Inject
    lateinit var adapterFactory: TopTracksAdapterFactory
    lateinit var tracksAdapter: TopTracksAdapter

    @Inject
    lateinit var statsViewModel: StatsViewModel

    private var timeRange = "long_term"
    private var amount = 20

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTopTracksBinding.bind(view)

        initRecyclerView()
        initObserving()
        getData()
        Log.e("TOP TRACKS FRAGMENT", "CREATED")
    }

    private fun getData() {
        statsViewModel.getUserTopTracks(timeRange, amount)
    }

    private fun initRecyclerView() {
        tracksAdapter = adapterFactory.provideTopTracksAdapter(
            Glide.with(requireContext())
        ) {
            navigateToTrackScreen(it)
        }

        binding.rvTopTracks.apply {
            adapter = tracksAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL))
        }
    }

    private fun initObserving() {
        statsViewModel.topTracks.observe(viewLifecycleOwner) {
            it.fold(
                onSuccess = { tracks ->
                    Log.e("TOP TRACKS RESULT", "Ayoooooo got tracks")
                    tracksAdapter.submitList(tracks)
                },
                onFailure = { ex ->
                    Log.e("STATS", "Some problem retrieving top tracks. ${ex.message}")
                    showMessage("shut up")
                }
            )
        }
    }

    private fun navigateToTrackScreen(id: String) {
//        val bundle = Bundle().apply {
//            putString("TRACK_ID", id) // todo: TRACK_ID to constant of TrackFragment
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
        showMessage("There should be navigation to track screen")
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            requireActivity().findViewById(R.id.container),
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}
