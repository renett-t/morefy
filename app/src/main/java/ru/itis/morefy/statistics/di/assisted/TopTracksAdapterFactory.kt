package ru.itis.morefy.statistics.di.assisted

import com.bumptech.glide.RequestManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import ru.itis.morefy.statistics.presentation.rv.TopTracksAdapter

@AssistedFactory
interface TopTracksAdapterFactory {

    fun provideTopTracksAdapter(
        @Assisted("glide") glide: RequestManager,
        @Assisted("onItemClickedAction") onItemClickedAction: (String) -> Unit,
    ): TopTracksAdapter

}
