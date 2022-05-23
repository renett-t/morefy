package ru.itis.morefy.core.presentation.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.itis.morefy.core.domain.models.Playlist

class UserPlaylistsAdapter @AssistedInject constructor(
    @Assisted("onItemClickedAction")
    private val onItemClickedAction: (String) -> Unit,
    @Assisted("glide")
    private val glide: RequestManager,
) : ListAdapter<Playlist, UserPlaylistHolder>(DiffUtilPlaylistItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserPlaylistHolder.create(parent, glide, onItemClickedAction)

    override fun onBindViewHolder(holder: UserPlaylistHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffUtilPlaylistItemCallback : DiffUtil.ItemCallback<Playlist>() {
    override fun areItemsTheSame(oldItem: Playlist, newItem: Playlist) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Playlist, newItem: Playlist) = oldItem == newItem
}
