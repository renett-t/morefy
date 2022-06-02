package ru.itis.morefy.core.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.morefy.core.domain.models.User
import ru.itis.morefy.core.domain.repository.UserDataRepository
import javax.inject.Inject

class GetUserProfileByUserId @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(userId:String): User {
        return try {
            withContext(dispatcher) {
                userDataRepository.getUserProfileByUserId(userId)
            }
        } catch (ex: Exception) {
            throw ex
        }
    }
}
