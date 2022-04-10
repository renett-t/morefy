package ru.itis.morefy.core.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.morefy.core.data.tokens.local.AuthDataException
import ru.itis.morefy.core.domain.models.TokenContainer
import ru.itis.morefy.core.domain.repository.SpotifyTokensRepository

class TokensViewModel(
    private val spotifyTokensRepository: SpotifyTokensRepository,
) : ViewModel() {

    private var _tokenContainer: SingleLiveEvent<Result<TokenContainer>> = SingleLiveEvent<Result<TokenContainer>>()
    val tokenContainer = _tokenContainer

    fun requestTokensByCode(code: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val token = spotifyTokensRepository.getTokensByCode(code)
                    withContext(Dispatchers.Main) {
                        if (token != null)
                            _tokenContainer.value = Result.success(token)
                        else
                            _tokenContainer.value = Result.failure(AuthDataException("No token found"))
                    }
                }
            } catch (e: Exception) {
                _tokenContainer.value = Result.failure(e)
            }
        }
    }
}

