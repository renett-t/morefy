package ru.itis.morefy.statistics.presentation.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.databinding.ItemTopArtistBinding

class TopArtistHolder(
    private val binding: ItemTopArtistBinding,
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

    fun bind(artist: Artist, position: Int) {
        this.artist = artist
        with(binding) {
            tvPosition.text = position.toString()
            tvArtist.text = artist.name
            glide.load(artist.imageUrl).into(ivCover)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup, glide: RequestManager,
            onItemClickedAction: (String) -> Unit
        ): TopArtistHolder {
            return TopArtistHolder(
                ItemTopArtistBinding.inflate(
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
