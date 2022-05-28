package ru.itis.morefy.statistics.presentation.tabs

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.morefy.R
import ru.itis.morefy.core.presentation.extensions.appComponent
import ru.itis.morefy.databinding.FragmentOverallStatsBinding
import ru.itis.morefy.statistics.presentation.viewmodel.StatsViewModel
import javax.inject.Inject

class OverallStatsFragment: Fragment(R.layout.fragment_overall_stats) {
    private lateinit var binding: FragmentOverallStatsBinding

    @Inject
    lateinit var viewModel: StatsViewModel

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
        TODO("Not yet implemented")
    }

    private fun startDownloadingData() {
        TODO("Not yet implemented")
    }

    private fun initRecyclers() {
        TODO("Not yet implemented")
    }
}
