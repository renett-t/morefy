package ru.itis.morefy.statistics.presentation.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.itis.morefy.core.domain.models.Artist

class TopArtistAdapter @AssistedInject constructor(
    @Assisted("onItemClickedAction")
    private val onItemClickedAction: (String) -> Unit,
    @Assisted("glide")
    private val glide: RequestManager,
) : ListAdapter<Artist, TopArtistHolder>(DiffUtilArtistItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TopArtistHolder.create(parent, glide, onItemClickedAction)

    override fun onBindViewHolder(holder: TopArtistHolder, position: Int) {
        holder.bind(getItem(position), position + 1)
    }
}

class DiffUtilArtistItemCallback : DiffUtil.ItemCallback<Artist>() {
    override fun areItemsTheSame(oldItem: Artist, newItem: Artist) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Artist, newItem: Artist) = oldItem == newItem
}
