package ru.itis.morefy.core.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.morefy.core.domain.models.Playlist
import ru.itis.morefy.core.domain.models.User
import ru.itis.morefy.core.domain.usecase.GetUserPlaylistsUseCase
import ru.itis.morefy.core.domain.usecase.GetUserProfileUseCase
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val getUserProfileUseCase: GetUserProfileUseCase,
                                        private val getUserPlaylistsUseCase: GetUserPlaylistsUseCase,
) : ViewModel() {

    private var _userData: MutableLiveData<Result<User>> = MutableLiveData()
    val userData: MutableLiveData<Result<User>> = _userData

    private var _playlists: MutableLiveData<Result<List<Playlist>>> = MutableLiveData()
    val playlists: MutableLiveData<Result<List<Playlist>>> = _playlists


    fun getUserData() {
        viewModelScope.launch {
            try {
                _userData.value = Result.success(getUserProfileUseCase())
            } catch (ex: Exception) {
                _userData.value = Result.failure(ex)
            }
        }
    }

    fun getPlaylists() {
        viewModelScope.launch {
            try {
                _playlists.value = Result.success(getUserPlaylistsUseCase())
            } catch (ex: Exception) {
                _playlists.value = Result.failure(ex)
            }
        }
    }

}