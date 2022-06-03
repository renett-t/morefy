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

    private var _userData1: MutableLiveData<Result<User>> = MutableLiveData()
    val userData1: MutableLiveData<Result<User>> = _userData1

    private var _userData2: MutableLiveData<Result<User>> = MutableLiveData()
    val userData2: MutableLiveData<Result<User>> = _userData2

    fun getUserData1(userId: String) {
        viewModelScope.launch {
            try {
                _userData1.value = Result.success(getUserProfileByUserId(userId))
            } catch (ex: Exception) {
                _userData1.value = Result.failure(ex)
            }
        }
    }

    fun getUserData2(userId: String) {
        viewModelScope.launch {
            try {
                _userData2.value = Result.success(getUserProfileByUserId(userId))
            } catch (ex: Exception) {
                _userData2.value = Result.failure(ex)
            }
        }
    }
}