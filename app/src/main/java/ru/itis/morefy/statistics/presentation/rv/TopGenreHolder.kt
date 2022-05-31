package ru.itis.morefy.statistics.presentation.rv

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.morefy.core.domain.models.Genre
import ru.itis.morefy.databinding.ItemTopGenreBinding

class TopGenreHolder(
    private val binding: ItemTopGenreBinding,
    private val onItemClickedAction: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var genre: Genre? = null
    private var percent: Int? = null

    init {
        itemView.setOnClickListener {
            onItemClickedAction()
        }
    }

    fun bind(genre: Genre, percent: Int) {
        this.genre = genre
        this.percent = percent
        with(binding) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                progress.setProgress(percent, false)
            }
            genreChip.text = genre.name
            tvPercentage.text = "" + percent + "%"
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onItemClickedAction: () -> Unit
        ): TopGenreHolder {
            return TopGenreHolder(
                ItemTopGenreBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onItemClickedAction
            )
        }
    }
}
