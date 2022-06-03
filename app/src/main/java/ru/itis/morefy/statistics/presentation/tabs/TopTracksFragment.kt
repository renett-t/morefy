package ru.itis.morefy.statistics.presentation.tabs

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.itis.morefy.R
import ru.itis.morefy.core.presentation.extensions.appComponent
import ru.itis.morefy.core.presentation.extensions.findNavigationController
import ru.itis.morefy.core.presentation.fragments.details.TRACK_ID_KEY
import ru.itis.morefy.databinding.FragmentTopTracksBinding
import ru.itis.morefy.statistics.di.assisted.TopTracksAdapterFactory
import ru.itis.morefy.statistics.presentation.rv.TopTracksAdapter
import ru.itis.morefy.statistics.presentation.viewmodel.StatsViewModel
import javax.inject.Inject

class TopTracksFragment : Fragment(R.layout.fragment_top_tracks) {
    private lateinit var binding: FragmentTopTracksBinding

    @Inject
    lateinit var adapterFactory: TopTracksAdapterFactory
    lateinit var tracksAdapter: TopTracksAdapter

    @Inject
    lateinit var statsViewModel: StatsViewModel


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
    }

    private fun getData() {
        statsViewModel.getUserTopTracks(
            statsViewModel.getTimeRange(), statsViewModel.getAmountToRequest()
        )
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
                    tracksAdapter.submitList(tracks)
                },
                onFailure = { ex ->
                    Log.e("STATS: TopTracksFragment", "Some problem retrieving top tracks. ${ex.message}")
                }
            )
        }
    }

    private fun navigateToTrackScreen(id: String) {
        val bundle = Bundle().apply {
            putString(TRACK_ID_KEY, id)
        }

        val options = NavOptions.Builder()
            .setLaunchSingleTop(true) // todo: add animations
            .build()

        requireActivity().findNavigationController(R.id.container).navigate(
            R.id.action_statisticsFragment_to_trackFragment,
            bundle,
            options
        )
    }
}
