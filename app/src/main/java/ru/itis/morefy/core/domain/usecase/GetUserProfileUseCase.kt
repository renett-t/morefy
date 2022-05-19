package ru.itis.morefy.core.domain.usecase

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.morefy.core.domain.exception.CredentialsExpiredException
import ru.itis.morefy.core.domain.models.User
import ru.itis.morefy.core.domain.repository.UserDataRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(): User {
        return try {
            withContext(dispatcher) {
                userDataRepository.getCurrentUserProfile()
            }
        } catch (ex: CredentialsExpiredException) {
            Log.e("CREDENTIALS", "While making request for user data, new token needed")
            throw ex // =)
        } catch (ex: Exception) {
            throw ex
        }
    }
}
