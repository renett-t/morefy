package ru.itis.morefy.statistics.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.itis.morefy.R
import ru.itis.morefy.databinding.FragmentStatisticsBinding
import ru.itis.morefy.statistics.di.StatisticsComponentViewModel
import ru.itis.morefy.statistics.di.assisted.AdapterFactory
import ru.itis.morefy.statistics.presentation.tabs.OverallStatsFragment
import ru.itis.morefy.statistics.presentation.tabs.TopArtistsFragment
import ru.itis.morefy.statistics.presentation.tabs.TopTracksFragment
import javax.inject.Inject

class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private lateinit var binding: FragmentStatisticsBinding

    @Inject
    lateinit var adapterFactory: AdapterFactory

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<StatisticsComponentViewModel>()
            .statisticsComponent.inject(this)
        super.onAttach(context)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStatisticsBinding.bind(view)

        initTabsAndViewPager()
    }

    private fun initTabsAndViewPager() {
        val listOfFragments = listOf<Fragment>(
            TopTracksFragment(),
            TopArtistsFragment(),
            OverallStatsFragment()
        )
        val adapter = adapterFactory.provideViewPagerAdapter(listOfFragments, this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) {
            tab, position -> {

            }
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.icon?.alpha = 250
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.icon?.alpha = 70
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}
