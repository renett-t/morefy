package ru.itis.morefy.statistics.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.itis.morefy.statistics.presentation.newInstance

class ViewPagerAdapter @AssistedInject constructor(
    @Assisted("listOfFragments")
    val list: List<Fragment>,
    @Assisted("fragment")
    val fragment: Fragment,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment = list[position].newInstance()
}
