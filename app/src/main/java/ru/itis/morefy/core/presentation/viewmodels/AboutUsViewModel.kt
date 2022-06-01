package ru.itis.morefy.core.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.morefy.core.domain.models.User
import ru.itis.morefy.core.domain.usecase.GetUserProfileByUserId
import javax.inject.Inject

class AboutUsViewModel @Inject constructor(private val getUserProfileByUserId: GetUserProfileByUserId
) : ViewModel() {

    private var _userData: MutableLiveData<Result<User>> = MutableLiveData()
    val userData: MutableLiveData<Result<User>> = _userData

    fun getUserData(userId: String) {
        viewModelScope.launch {
            try {
                _userData.value = Result.success(getUserProfileByUserId(userId))
            } catch (ex: Exception) {
                _userData.value = Result.failure(ex)
            }
        }
    }

    fun getUserData2(userId: String) {
        viewModelScope.launch {
            try {
                _userData.value = Result.success(getUserProfileByUserId(userId))
            } catch (ex: Exception) {
                _userData.value = Result.failure(ex)
            }
        }
    }
}