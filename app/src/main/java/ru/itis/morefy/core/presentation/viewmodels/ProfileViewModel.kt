package ru.itis.morefy.core.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.morefy.core.domain.models.User
import ru.itis.morefy.core.domain.usecase.GetUserProfileUseCase
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase
) : ViewModel() {

    private var _userData: MutableLiveData<Result<User>> = MutableLiveData()
    val userData: MutableLiveData<Result<User>> = _userData

    fun getUserData() {
        viewModelScope.launch {
            try {
                _userData.value = Result.success(getUserProfileUseCase())
            } catch (ex: Exception) {
                ex.message?.let {
                    Log.e("VIEW MODEL", it)
                }
                _userData.value = Result.failure(ex)
            }
        }
    }
}
