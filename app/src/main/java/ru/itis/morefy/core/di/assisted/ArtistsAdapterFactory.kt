package ru.itis.morefy.core.di.assisted

import com.bumptech.glide.RequestManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import ru.itis.morefy.core.presentation.fragments.details.rv.track.ArtistsAdapter

@AssistedFactory
interface ArtistsAdapterFactory {

    fun provideArtistsAdapter(
        @Assisted("glide") glide: RequestManager,
        @Assisted("onItemClickedActionArtistsAdapter") onItemClickedAction: (String) -> Unit,
    ): ArtistsAdapter
}
