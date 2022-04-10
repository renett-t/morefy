package ru.itis.morefy.core.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.itis.morefy.core.domain.repository.SpotifyTokensRepository

class ViewModelFactory(
    private val spotifyTokensRepository: SpotifyTokensRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TokensViewModel::class.java) ->
                TokensViewModel(spotifyTokensRepository) as T
            else ->
                throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
