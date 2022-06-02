package ru.itis.morefy.core.presentation.fragments.details.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.itis.morefy.core.domain.models.Artist

class ArtistsAdapter @AssistedInject constructor(
    @Assisted("onItemClickedActionArtistsAdapter")
    private val onItemClickedAction: (String) -> Unit,
    @Assisted("glide")
    private val glide: RequestManager,
) : ListAdapter<Artist, ArtistHolder>(DiffUtilArtistItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ArtistHolder.create(parent, glide, onItemClickedAction)

    override fun onBindViewHolder(holder: ArtistHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffUtilArtistItemCallback : DiffUtil.ItemCallback<Artist>() {
    override fun areItemsTheSame(oldItem: Artist, newItem: Artist) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Artist, newItem: Artist) = oldItem == newItem
}
