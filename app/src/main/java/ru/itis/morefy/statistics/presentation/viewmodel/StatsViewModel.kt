package ru.itis.morefy.statistics.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.morefy.core.data.api.MAX_LIMIT_AMOUNT
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Genre
import ru.itis.morefy.core.domain.models.TimeRange
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.core.domain.models.features.AverageTracksFeatures
import ru.itis.morefy.statistics.domain.usecase.GetUserTopArtistsUseCase
import ru.itis.morefy.statistics.domain.usecase.GetUserTopTracksUseCase
import ru.itis.morefy.statistics.domain.service.UserStatsService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StatsViewModel @Inject constructor(
    private val getUserTopTracksUseCase: GetUserTopTracksUseCase,
    private val getUserTopArtistsUseCase: GetUserTopArtistsUseCase,
    private val userStatsService: UserStatsService
) : ViewModel() {

    private var timeRange: TimeRange = TimeRange.UNDEFINED
    private var amount: Int = MAX_LIMIT_AMOUNT

    private var _topTracks: MutableLiveData<Result<List<Track>>> = MutableLiveData()
    val topTracks: MutableLiveData<Result<List<Track>>> = _topTracks

    private var _topArtists: MutableLiveData<Result<List<Artist>>> = MutableLiveData()
    val topArtists: LiveData<Result<List<Artist>>> = _topArtists

    private var _overallStats: MutableLiveData<Result<AverageTracksFeatures>> = MutableLiveData()
    val overallStats: LiveData<Result<AverageTracksFeatures>> = _overallStats

    private var _topGenres: MutableLiveData<Result<Map<Genre, Int>>> = MutableLiveData()
    val topGenres: LiveData<Result<Map<Genre, Int>>> = _topGenres

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: MutableLiveData<Exception> = _error

    fun getUserTopTracks(timeRange: TimeRange, amount: Int) {
        viewModelScope.launch {
            try {
                val list = getUserTopTracksUseCase(timeRange.time, amount)
                _topTracks.value = Result.success(list)
            } catch (ex: Exception) {
                _topTracks.value = Result.failure(ex)
            }
        }
    }

    fun getUserTopArtists(timeRange: TimeRange, amount: Int) {
        viewModelScope.launch {
            try {
                val list = getUserTopArtistsUseCase(timeRange.time, amount)
                _topArtists.value = Result.success(list)
            } catch (ex: Exception) {
                _topArtists.value = Result.failure(ex)
            }
        }
    }

    fun getUserTopGenres(timeRange: TimeRange) {
        viewModelScope.launch {
            try {
                val map = userStatsService.getCurrentUserTopGenresByTopArtists(timeRange)
                _topGenres.value = Result.success(map)
            } catch (ex: Exception) {
                _topGenres.value = Result.failure(ex)
            }
        }
    }

    fun getOverallListeningStats(timeRange: TimeRange) {
        viewModelScope.launch {
            try {
                val stats = userStatsService.getUserOverallListeningStats(timeRange)
                _overallStats.value = Result.success(stats)
            } catch (ex: Exception) {
                _overallStats.value = Result.failure(ex)
            }
        }
    }

    fun reloadData() {
        this.getUserTopTracks(timeRange, amount)
        this.getUserTopArtists(timeRange, amount)
        this.getUserTopGenres(timeRange)
        this.getOverallListeningStats(timeRange)
    }

    fun getTimeRange(): TimeRange {
        return timeRange
    }

    fun setTimeRange(newRange: TimeRange) {
        Log.e("STATS VIEW MODEL", "Setting new time range to value = $newRange")
        this.timeRange = newRange
    }

    fun getAmountToRequest(): Int {
        return amount
    }

    fun setAmountToRequest(newAmount: Int) {
        this.amount = newAmount
    }
}
