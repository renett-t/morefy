package ru.itis.morefy.core.presentation.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.itis.morefy.core.domain.models.Playlist
import ru.itis.morefy.core.domain.models.User
import ru.itis.morefy.databinding.ItemPlaylistBinding

class UserPlaylistHolder(
    private val binding: ItemPlaylistBinding,
    private val glide: RequestManager,
    private val onItemClickedAction: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var playlist: Playlist? = null

    init {
        itemView.setOnClickListener {
            playlist?.let {
                onItemClickedAction(it.id)
            }
        }
    }

    fun bind(playlist: Playlist) {
        this.playlist = playlist
        with(binding) {
            glide.load(playlist.imageUrl).into(ivPlaylistCover)
            tvPlaylistTitle.text = playlist.name
            tvAuthor.text = playlist.owner.name
            tvTracksCount.text = playlist.tracksCount.toString()
        }
    }

    companion object {
        fun create(
            parent: ViewGroup, glide: RequestManager,
            onItemClickedAction: (String) -> Unit
        ): UserPlaylistHolder {
            return UserPlaylistHolder(
                ItemPlaylistBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                glide,
                onItemClickedAction
            )
        }
    }
}
