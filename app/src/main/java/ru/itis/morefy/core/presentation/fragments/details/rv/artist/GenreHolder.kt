package ru.itis.morefy.core.presentation.fragments.details.rv.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.morefy.core.domain.models.Genre
import ru.itis.morefy.databinding.ItemGenreBinding

class GenreHolder(
    private val binding: ItemGenreBinding,
    private val onItemClickedAction: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var genre: Genre? = null

    init {
        itemView.setOnClickListener {
            onItemClickedAction()
        }
    }

    fun bind(genre: Genre) {
        this.genre = genre
        with(binding) {
            tvGenreName.text = genre.name
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onItemClickedAction: () -> Unit
        ): GenreHolder {
            return GenreHolder(
                ItemGenreBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onItemClickedAction
            )
        }
    }
}
