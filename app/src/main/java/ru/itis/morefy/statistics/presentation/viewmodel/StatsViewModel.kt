package ru.itis.morefy.statistics.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.morefy.core.domain.exception.CredentialsExpiredException
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.statistics.domain.usecase.GetUserOverallListeningStatsUseCase
import ru.itis.morefy.statistics.domain.usecase.GetUserTopArtistsUseCase
import ru.itis.morefy.statistics.domain.usecase.GetUserTopTracksUseCase
import ru.itis.morefy.statistics.domain.models.OverallListeningStats
import javax.inject.Inject

class StatsViewModel @Inject constructor(
    private val getUserTopTracksUseCase: GetUserTopTracksUseCase,
    private val getUserTopArtistsUseCase: GetUserTopArtistsUseCase,
    private val getUserOverallListeningStatsUseCase: GetUserOverallListeningStatsUseCase,
) : ViewModel() {

    private var _topTracks: MutableLiveData<Result<List<Track>>> = MutableLiveData()
    val topTracks: MutableLiveData<Result<List<Track>>> = _topTracks

    private var _topArtists: MutableLiveData<Result<List<Artist>>> = MutableLiveData()
    val topArtists: LiveData<Result<List<Artist>>> = _topArtists

    private var _overallStats: MutableLiveData<Result<OverallListeningStats>> = MutableLiveData()
    val overallStats: LiveData<Result<OverallListeningStats>> = _overallStats

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: MutableLiveData<Exception> = _error

    fun getUserTopTracks(timeRange: String, amount: Int) {
        viewModelScope.launch {
            try {
                val list = getUserTopTracksUseCase(timeRange, amount)
                _topTracks.value = Result.success(list)
            } catch (ex: CredentialsExpiredException) {
//                _topTracks.value = Result.failure(ex)
                _error.value = ex
            } catch (ex: Exception) {
//                _topTracks.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun getUserTopArtists(timeRange: String, amount: Int) {
        viewModelScope.launch {
            try {
                val list = getUserTopArtistsUseCase(timeRange, amount)
                _topArtists.value = Result.success(list)
            } catch (ex: CredentialsExpiredException) {
//                _topArtists.value = Result.failure(ex)
                _error.value = ex
            } catch (ex: Exception) {
//                _topArtists.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    fun getOverallListeningStats(timeRange: String) {
        viewModelScope.launch {
            try {
                val stats = getUserOverallListeningStatsUseCase(timeRange)
                _overallStats.value = Result.success(stats)
            } catch (ex: CredentialsExpiredException) {
//                _overallStats.value = Result.failure(ex)
                _error.value = ex
            } catch (ex: Exception) {
//                _overallStats.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }
}
