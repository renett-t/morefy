package ru.itis.morefy.statistics.presentation.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.core.presentation.rv.DiffUtilTrackItemCallback

class TopTracksAdapter @AssistedInject constructor(
    @Assisted("onItemClickedAction")
    private val onItemClickedAction: (String) -> Unit,
    @Assisted("glide")
    private val glide: RequestManager,
) : ListAdapter<Track, TopTrackHolder>(DiffUtilTrackItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TopTrackHolder.create(parent, glide, onItemClickedAction)

    override fun onBindViewHolder(holder: TopTrackHolder, position: Int) {
        holder.bind(getItem(position), position + 1)
    }
}

