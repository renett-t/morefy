package ru.itis.morefy.core.di.assisted

import com.bumptech.glide.RequestManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import ru.itis.morefy.core.presentation.rv.UserPlaylistsAdapter

@AssistedFactory
interface UserPlaylistAdapterFactory {

    fun provideUserPlaylistsAdapter(
        @Assisted("glide") glide: RequestManager,
        @Assisted("onItemClickedAction") onItemClickedAction: (String) -> Unit,
    ): UserPlaylistsAdapter

}
