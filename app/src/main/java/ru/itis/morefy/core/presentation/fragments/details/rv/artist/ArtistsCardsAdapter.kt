package ru.itis.morefy.core.presentation.fragments.details.rv.artist

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.presentation.fragments.details.rv.track.ArtistHolder
import ru.itis.morefy.core.presentation.fragments.details.rv.track.DiffUtilArtistItemCallback

class ArtistsCardsAdapter @AssistedInject constructor(
    @Assisted
    private val onItemClickedAction: (String) -> Unit,
    @Assisted
    private val glide: RequestManager,
) : ListAdapter<Artist, ArtistHolder>(DiffUtilArtistItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ArtistHolder.create(parent, glide, onItemClickedAction)

    override fun onBindViewHolder(holder: ArtistHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
