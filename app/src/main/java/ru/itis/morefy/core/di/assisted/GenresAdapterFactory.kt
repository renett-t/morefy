package ru.itis.morefy.core.di.assisted

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import ru.itis.morefy.core.presentation.fragments.details.rv.artist.GenresAdapter

@AssistedFactory
interface GenresAdapterFactory {

    fun provideGenresAdapter(
        @Assisted onItemClickedAction: () -> Unit,
    ): GenresAdapter
}
