package ru.itis.morefy.core.presentation.fragments.details.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.databinding.ItemArtistBinding

class ArtistHolder(
    private val binding: ItemArtistBinding,
    private val glide: RequestManager,
    private val onItemClickedAction: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var artist: Artist? = null

    init {
        itemView.setOnClickListener {
            artist?.let {
                onItemClickedAction(it.id)
            }
        }
    }

    fun bind(artist: Artist) {
        this.artist = artist
        with(binding) {
            tvArtistName.text = artist.name
            glide.load(artist.imageUrl).into(ivArtistCover)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup, glide: RequestManager,
            onItemClickedAction: (String) -> Unit
        ): ArtistHolder {
            return ArtistHolder(
                ItemArtistBinding.inflate(
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
