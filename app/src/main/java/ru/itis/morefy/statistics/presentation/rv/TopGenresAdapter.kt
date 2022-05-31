package ru.itis.morefy.statistics.presentation.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.itis.morefy.core.domain.models.Genre

class TopGenreAdapter @AssistedInject constructor(
    @Assisted("onItemClickedActionTopGenre")
    private val onItemClickedAction: () -> Unit,
) : ListAdapter<Map.Entry<Genre, Int>, TopGenreHolder>(DiffUtilGenreItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TopGenreHolder.create(parent, onItemClickedAction)

    override fun onBindViewHolder(holder: TopGenreHolder, position: Int) {
        holder.bind(getItem(position).key, getItem(position).value)
    }
}

class DiffUtilGenreItemCallback : DiffUtil.ItemCallback<Map.Entry<Genre, Int>>() {
    override fun areItemsTheSame(oldItem: Map.Entry<Genre, Int>, newItem: Map.Entry<Genre, Int>) =
        oldItem.key == newItem.key

    override fun areContentsTheSame(
        oldItem: Map.Entry<Genre, Int>,
        newItem: Map.Entry<Genre, Int>
    ) = oldItem == newItem
}
