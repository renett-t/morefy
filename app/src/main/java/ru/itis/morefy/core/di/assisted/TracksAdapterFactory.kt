package ru.itis.morefy.core.di.assisted

import com.bumptech.glide.RequestManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import ru.itis.morefy.core.presentation.rv.TracksAdapter

@AssistedFactory
interface TracksAdapterFactory {

    fun provideTracksAdapter(
        @Assisted glide: RequestManager,
        @Assisted onItemClickedAction: (String) -> Unit,
    ): TracksAdapter
}
