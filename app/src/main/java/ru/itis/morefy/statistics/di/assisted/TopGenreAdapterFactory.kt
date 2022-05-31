package ru.itis.morefy.statistics.di.assisted

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import ru.itis.morefy.statistics.presentation.rv.TopGenreAdapter

@AssistedFactory
interface TopGenreAdapterFactory {

    fun provideTopGenresAdapter(
        @Assisted("onItemClickedActionTopGenre") onItemClickedAction: () -> Unit,
    ): TopGenreAdapter
}
