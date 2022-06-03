package ru.itis.morefy.core.presentation.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.databinding.ItemTrackBinding

class TrackHolder(
    private val binding: ItemTrackBinding,
    private val glide: RequestManager,
    private val onItemClickedAction: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var track: Track? = null

    init {
        itemView.setOnClickListener {
            track?.let {
                onItemClickedAction(it.id)
            }
        }
    }

    fun bind(track: Track) {
        this.track = track
        with(binding) {
            tvArtistName.text = getArtistsNames(track.artists)
            tvTrackName.text = track.name
            glide.load(track.album.imageUrl).into(ivCover)
            if (track.isExplicit)
                ivExplicit.visibility = View.VISIBLE
        }
    }

    private fun getArtistsNames(artists: List<Artist>): String {
        val result = StringBuilder().append("")
        val delimiter = ", "
        for (artist in artists) {
            result.append(artist.name)
                .append(delimiter)
        }
        result.replace(result.length - delimiter.length, result.length, "")

        return result.toString()
    }

    companion object {
        fun create(
            parent: ViewGroup, glide: RequestManager,
            onItemClickedAction: (String) -> Unit
        ): TrackHolder {
            return TrackHolder(
                ItemTrackBinding.inflate(
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
