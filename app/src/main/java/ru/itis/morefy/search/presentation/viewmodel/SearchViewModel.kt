package ru.itis.morefy.search.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.search.domain.usecase.GetArtistsBySearchQueryUseCase
import ru.itis.morefy.search.domain.usecase.GetTracksBySearchQueryUseCase
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getArtistsBySearchQueryUseCase: GetArtistsBySearchQueryUseCase,
    private val getTracksBySearchQueryUseCase: GetTracksBySearchQueryUseCase,
) : ViewModel() {
    private val DEFAULT_AMOUNT = 10

    private var _foundArtists: MutableLiveData<Result<List<Artist>>> = MutableLiveData()
    val foundArtists: MutableLiveData<Result<List<Artist>>> = _foundArtists

    private var _foundTracks: MutableLiveData<Result<List<Track>>> = MutableLiveData()
    val foundTracks: MutableLiveData<Result<List<Track>>> = _foundTracks

    fun getArtistsByQuery(query: String) {
        viewModelScope.launch {
            try {
                _foundArtists.value = Result.success(getArtistsBySearchQueryUseCase(query, DEFAULT_AMOUNT))
            } catch (ex: Exception) {
                _foundArtists.value = Result.failure(ex)
            }
        }
    }

    fun getPopularTracks(query: String) {
        viewModelScope.launch {
            try {
                _foundTracks.value = Result.success(getTracksBySearchQueryUseCase(query, DEFAULT_AMOUNT))
            } catch (ex: Exception) {
                _foundTracks.value = Result.failure(ex)
            }
        }
    }
}
