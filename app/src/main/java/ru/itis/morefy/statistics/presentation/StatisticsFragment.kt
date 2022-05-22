package ru.itis.morefy.statistics.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.itis.morefy.R
import ru.itis.morefy.core.presentation.extensions.appComponent
import ru.itis.morefy.databinding.FragmentStatisticsBinding
import ru.itis.morefy.statistics.di.assisted.AdapterFactory
import ru.itis.morefy.statistics.presentation.tabs.OverallStatsFragment
import ru.itis.morefy.statistics.presentation.tabs.TopArtistsFragment
import ru.itis.morefy.statistics.presentation.tabs.TopTracksFragment
import ru.itis.morefy.statistics.presentation.viewmodel.StatsViewModel
import javax.inject.Inject

private fun Fragment.getTitle(context: Context): String {
    return when(this) {
        is TopTracksFragment -> context.getString(R.string.top_tracks)
        is TopArtistsFragment -> context.getString(R.string.top_artists)
        else -> context.getString(R.string.overall)
    }
}

fun Fragment.newInstance(): Fragment {
    val fragment = when (this) {
        is TopTracksFragment -> TopTracksFragment()
        is TopArtistsFragment -> TopArtistsFragment()
        else -> OverallStatsFragment()
    }
    val args = Bundle()
    fragment.arguments = args
    return fragment
}

class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private lateinit var binding: FragmentStatisticsBinding

    @Inject
    lateinit var adapterFactory: AdapterFactory

    @Inject
    lateinit var statsViewModel: StatsViewModel

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStatisticsBinding.bind(view)

        initTabsAndViewPager()
        initChangeTimeRange()
        initObserversOnViewModel()
    }

    private fun initTabsAndViewPager() {
        val listOfFragments = listOf<Fragment>(
            TopTracksFragment(),
            TopArtistsFragment(),
            OverallStatsFragment()
        )
        binding.viewPager.adapter = adapterFactory.provideViewPagerAdapter(listOfFragments, this)

        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.icon?.alpha = 250
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.icon?.alpha = 70
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = context?.let { listOfFragments[position].getTitle(it) }
        }.attach()
    }

    private fun initObserversOnViewModel() {
        statsViewModel.error.observe(viewLifecycleOwner) {

        }
    }

    private fun initChangeTimeRange() {
        // todo add button to change time range and pass that variable to fragments
    }
}

