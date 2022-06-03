package ru.itis.morefy.statistics.presentation.tabs

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import ru.itis.morefy.R
import ru.itis.morefy.core.domain.models.Genre
import ru.itis.morefy.core.domain.models.features.AverageTracksFeatures
import ru.itis.morefy.core.domain.models.features.FeaturesUtils
import ru.itis.morefy.core.presentation.chart.ChartDrawer
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

    @Inject
    lateinit var chartDrawer: ChartDrawer

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

    private fun initRecyclers() {
        genreAdapter = factory.provideTopGenresAdapter {
            // do nothing =)
        }
        with(binding) {
            rvTopGenres.apply {
                adapter = genreAdapter
                addItemDecoration(
                    DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL)
                )
            }
        }
    }

    private fun initObservers() {
        viewModel.topGenres.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = { map ->
                    genreAdapter.submitList(
                        sortValues(map.entries.toList())
                    )
                },
                onFailure = {
                    Log.e("STATS: TopGenresFragment", "Some problem retrieving top genres. ${it.message}")
                }
            )
        }
        viewModel.overallStats.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = {
                    updateChartView(it)
                },
                onFailure = {
                    Log.e("STATS: TopGenresFragment", "Some problem retrieving average stats. ${it.message}")
                }
            )
        }
    }

    private fun startDownloadingData() {
        viewModel.getUserTopGenres(viewModel.getTimeRange())
        viewModel.getOverallListeningStats(viewModel.getTimeRange())
    }

    private fun updateChartView(avgFeatures: AverageTracksFeatures) {
        chartDrawer.drawRadarChart(
            requireContext(),
            binding.radarChartStats.radarChart,
            getString(R.string.averall_listening_stats),
            FeaturesUtils.toMap(requireContext(), avgFeatures)
        )
    }

    private fun sortValues(list: List<Map.Entry<Genre, Int>>): List<Map.Entry<Genre, Int>> {
        return list.sortedByDescending { it.value }
    }
}
