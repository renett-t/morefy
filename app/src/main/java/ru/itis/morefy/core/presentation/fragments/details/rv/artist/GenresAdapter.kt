package ru.itis.morefy.core.presentation.fragments.details.rv.artist

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.itis.morefy.core.domain.models.Genre

class GenresAdapter @AssistedInject constructor(
    @Assisted private val onItemClickedAction: () -> Unit,
) : ListAdapter<Genre, GenreHolder>(DiffUtilGenreItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GenreHolder.create(parent, onItemClickedAction)

    override fun onBindViewHolder(holder: GenreHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffUtilGenreItemCallback : DiffUtil.ItemCallback<Genre>() {
    override fun areItemsTheSame(oldItem: Genre, newItem: Genre) =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: Genre,
        newItem: Genre
    ) = oldItem == newItem
}
