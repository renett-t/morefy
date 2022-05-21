package ru.itis.morefy.core.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.repository.UserDataRepository
import javax.inject.Inject

class GetFollowedArtistsUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(): List<Artist> {
        return try {
            withContext(dispatcher) {
                userDataRepository.getCurrentUserFollowedArtists()
            }
        } catch (ex: Exception) {
            throw ex
        }
    }

    suspend fun getCount(): Int {
        return withContext(dispatcher) {
            userDataRepository.getCurrentUserFollowedArtists().size
        }
    }
}
