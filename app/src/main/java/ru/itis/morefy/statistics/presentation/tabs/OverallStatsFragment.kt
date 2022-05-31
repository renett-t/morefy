package ru.itis.morefy.statistics.presentation.tabs

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import ru.itis.morefy.R
import ru.itis.morefy.core.presentation.extensions.appComponent
import ru.itis.morefy.databinding.FragmentOverallStatsBinding
import ru.itis.morefy.statistics.di.assisted.TopGenreAdapterFactory
import ru.itis.morefy.statistics.presentation.rv.TopGenreAdapter
import ru.itis.morefy.statistics.presentation.viewmodel.StatsViewModel
import javax.inject.Inject

class OverallStatsFragment : Fragment(R.layout.fragment_overall_stats) {
    private lateinit var binding: FragmentOverallStatsBinding

    @Inject
    lateinit var viewModel: StatsViewModel

    @Inject
    lateinit var factory: TopGenreAdapterFactory
    private lateinit var genreAdapter: TopGenreAdapter

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOverallStatsBinding.bind(view)

        initRecyclers()
        initObservers()
        startDownloadingData()
    }

    private fun initObservers() {
        viewModel.topGenres.observe(viewLifecycleOwner) {
            it.fold(
                onSuccess = { map ->
                    genreAdapter.submitList(map.entries.toList())
                },
                onFailure = {
                    Log.e("STATS: TopGenresFragment", "Some problem retrieving top genres. ${it.message}")
                }
            )
        }
    }

    private fun startDownloadingData() {
        viewModel.getTopGenres()
    }

    private fun initRecyclers() {
        genreAdapter = factory.provideTopGenresAdapter {
            // do nothing =)
        }
        with(binding) {
            rvTopGenres.apply {
                adapter = genreAdapter
                addItemDecoration(
                    DividerItemDecoration(requireContext(), RecyclerView.SCROLL_AXIS_VERTICAL)
                )
            }
        }
    }
}
