package ru.itis.morefy.core.di.assisted

import com.bumptech.glide.RequestManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import ru.itis.morefy.core.presentation.fragments.details.rv.artist.ArtistsCardsAdapter

@AssistedFactory
interface ArtistsCardsAdapterFactory {

    fun provideArtistsCardsAdapter(
        @Assisted glide: RequestManager,
        @Assisted onItemClickedAction: (String) -> Unit,
    ): ArtistsCardsAdapter
}
