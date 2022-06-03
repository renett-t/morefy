package ru.itis.morefy.core.presentation.viewmodels.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.core.domain.usecase.artist.GetArtistPopularTracksUseCase
import ru.itis.morefy.core.domain.usecase.artist.GetArtistUseCase
import ru.itis.morefy.core.domain.usecase.artist.GetSimilarArtistsUseCase
import javax.inject.Inject

class ArtistViewModel @Inject constructor(
    private val getArtistUseCase: GetArtistUseCase,
    private val getSimilarArtistsUseCase: GetSimilarArtistsUseCase,
    private val getArtistPopularTracksUseCase: GetArtistPopularTracksUseCase
) : ViewModel() {

    private var _artist: MutableLiveData<Result<Artist>> = MutableLiveData()
    val artist: MutableLiveData<Result<Artist>> = _artist

    private var _similarArtists: MutableLiveData<Result<List<Artist>>> = MutableLiveData()
    val similarArtists: MutableLiveData<Result<List<Artist>>> = _similarArtists

    private var _popularTracks: MutableLiveData<Result<List<Track>>> = MutableLiveData()
    val popularTracks: MutableLiveData<Result<List<Track>>> = _popularTracks

    fun getArtistInfo(id: String) {
        viewModelScope.launch {
            try {
                _artist.value = Result.success(getArtistUseCase(id))
            } catch (ex: Exception) {
                _artist.value = Result.failure(ex)
            }
        }
    }

    fun getSimilarArtists(id: String) {
        viewModelScope.launch {
            try {
                _similarArtists.value = Result.success(getSimilarArtistsUseCase(id))
            } catch (ex: Exception) {
                _similarArtists.value = Result.failure(ex)
            }
        }
    }

    fun getPopularTracks(id: String) {
        viewModelScope.launch {
            try {
                _popularTracks.value = Result.success(getArtistPopularTracksUseCase(id))
            } catch (ex: Exception) {
                _popularTracks.value = Result.failure(ex)
            }
        }
    }
}
