package ru.itis.morefy.statistics.di.assisted

import androidx.fragment.app.Fragment
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import ru.itis.morefy.statistics.presentation.adapter.ViewPagerAdapter

@AssistedFactory
interface AdapterFactory {

    fun provideViewPagerAdapter(
        @Assisted("listOfFragments") list: List<Fragment>,
        @Assisted("fragment") fragment: Fragment
    ): ViewPagerAdapter

}
