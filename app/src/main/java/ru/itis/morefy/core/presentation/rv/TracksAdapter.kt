package ru.itis.morefy.core.presentation.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.itis.morefy.core.domain.models.Track

class TracksAdapter @AssistedInject constructor(
    private val onItemClickedAction: (String) -> Unit,
    private val glide: RequestManager,
) : ListAdapter<Track, TrackHolder>(DiffUtilTrackItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TrackHolder.create(parent, glide, onItemClickedAction)

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffUtilTrackItemCallback : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Track, newItem: Track) = oldItem == newItem
}
