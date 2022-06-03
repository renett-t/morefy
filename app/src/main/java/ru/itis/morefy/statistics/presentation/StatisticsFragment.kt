package ru.itis.morefy.statistics.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.itis.morefy.R
import ru.itis.morefy.core.data.api.MAX_LIMIT_AMOUNT
import ru.itis.morefy.core.domain.models.TimeRange
import ru.itis.morefy.core.presentation.extensions.appComponent
import ru.itis.morefy.core.presentation.extensions.showMessage
import ru.itis.morefy.databinding.FragmentStatisticsBinding
import ru.itis.morefy.statistics.di.assisted.AdapterFactory
import ru.itis.morefy.statistics.presentation.tabs.OverallStatsFragment
import ru.itis.morefy.statistics.presentation.tabs.TopArtistsFragment
import ru.itis.morefy.statistics.presentation.tabs.TopTracksFragment
import ru.itis.morefy.statistics.presentation.viewmodel.StatsViewModel
import javax.inject.Inject

private fun Fragment.getTitle(context: Context): String {
    return when (this) {
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

class StatisticsFragment: Fragment(R.layout.fragment_statistics) {

    private lateinit var binding: FragmentStatisticsBinding

    @Inject
    lateinit var adapterFactory: AdapterFactory

    @Inject
    lateinit var statsViewModel: StatsViewModel

    private var isFabClicked = false

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        initViewModelParams()
        super.onAttach(context)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStatisticsBinding.bind(view)

        initTabsAndViewPager()
        initChangeTimeRange()
        initObserversOnViewModel()
    }

    private fun initViewModelParams() {
        statsViewModel.setTimeRange(TimeRange.SHORT)
        statsViewModel.setAmountToRequest(MAX_LIMIT_AMOUNT)
    }

    private fun initTabsAndViewPager() {
        val listOfFragments = listOf<Fragment>(
            TopTracksFragment(),
            TopArtistsFragment(),
            OverallStatsFragment()
        )
        binding.viewPager.adapter = adapterFactory.provideViewPagerAdapter(listOfFragments, this)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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
        with(binding) {
            fabChangeTimeRange.setOnClickListener {
                changeVisibilityOfFabs(isFabClicked)
                setAnimationsOnFabs(isFabClicked)
                setClickableFabs(!isFabClicked)
                isFabClicked = !isFabClicked
            }
            fabChangeTimeRange.setOnLongClickListener {
                showMessage(getString(R.string.time_range_change_button))
                true
            }

            fabOneMonth.setOnClickListener{
                statsViewModel.setTimeRange(TimeRange.SHORT)
                startReloadingData()
            }
            fabOneMonth.setOnLongClickListener {
                showMessage(getString(R.string.time_range_short_explained))
                true
            }

            fabSixMonths.setOnClickListener{
                statsViewModel.setTimeRange(TimeRange.MEDIUM)
                startReloadingData()
            }
            fabSixMonths.setOnLongClickListener {
                showMessage(getString(R.string.time_range_medium_explained))
                true
            }

            fabAllTime.setOnClickListener{
                statsViewModel.setTimeRange(TimeRange.LONG)
                startReloadingData()
            }
            fabAllTime.setOnLongClickListener {
                showMessage(getString(R.string.time_range_long_explained))
                true
            }
        }
    }

    private fun setClickableFabs(clickable: Boolean) {
        with(binding) {
            fabSixMonths.isEnabled = clickable
            fabOneMonth.isEnabled = clickable
            fabAllTime.isEnabled = clickable
        }
    }

    private fun changeVisibilityOfFabs(fabClicked: Boolean) {
        with(binding) {
            if (fabClicked) {
                fabSixMonths.visibility = View.VISIBLE
                fabOneMonth.visibility = View.VISIBLE
                fabAllTime.visibility = View.VISIBLE
            } else {
                fabSixMonths.visibility = View.INVISIBLE
                fabOneMonth.visibility = View.INVISIBLE
                fabAllTime.visibility = View.INVISIBLE
            }
        }
    }

    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_open_anim)
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_close_anim)
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.from_button_anim)
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.to_button_anim)
    }

    private fun setAnimationsOnFabs(fabClicked: Boolean) {
        with(binding) {
            if (fabClicked) {
                fabChangeTimeRange.startAnimation(rotateClose)
                fabSixMonths.startAnimation(toBottom)
                fabOneMonth.startAnimation(toBottom)
                fabAllTime.startAnimation(toBottom)
            } else {
                fabChangeTimeRange.startAnimation(rotateOpen)
                fabSixMonths.startAnimation(fromBottom)
                fabOneMonth.startAnimation(fromBottom)
                fabAllTime.startAnimation(fromBottom)
            }
        }
    }

    private fun startReloadingData() {
        statsViewModel.reloadData()
    }
}

