package ru.itis.morefy.core.presentation.viewmodels.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.core.domain.models.features.TrackFeatures
import ru.itis.morefy.core.domain.usecase.artist.GetArtistUseCase
import ru.itis.morefy.core.domain.usecase.GetTrackUseCase
import javax.inject.Inject

class TrackViewModel @Inject constructor(
    private val getTrackUseCase: GetTrackUseCase,
    private val getArtistUseCase: GetArtistUseCase
) : ViewModel() {

    private var _track: MutableLiveData<Result<Track>> = MutableLiveData()
    val track: MutableLiveData<Result<Track>> = _track

    private var _trackFeatures: MutableLiveData<Result<TrackFeatures>> = MutableLiveData()
    val trackFeatures: MutableLiveData<Result<TrackFeatures>> = _trackFeatures

    private var _artists: MutableLiveData<Result<List<Artist>>> = MutableLiveData()
    val artists: MutableLiveData<Result<List<Artist>>> = _artists

    fun getTrackInfo(id: String) {
        viewModelScope.launch {
            try {
                _track.value = Result.success(getTrackUseCase(id))
            } catch (ex: Exception) {
                _track.value = Result.failure(ex)
            }
        }
    }

    fun getTrackFeaturesInfo(id: String) {
        viewModelScope.launch {
            try {
                _trackFeatures.value = Result.success(getTrackUseCase.getTrackFeatures(id))
            } catch (ex: Exception) {
                _trackFeatures.value = Result.failure(ex)
            }
        }
    }

    fun getArtists(ids: List<String>) {
        viewModelScope.launch {
            try {
                _artists.value = Result.success(getArtistUseCase.getMultiple(ids))
            } catch (ex: Exception) {
                _artists.value = Result.failure(ex)
            }
        }
    }

}
