package ru.itis.morefy.statistics.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.morefy.core.domain.exception.CredentialsExpiredException
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.core.domain.repository.UserDataRepository
import javax.inject.Inject

class GetUserTopTracksUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke (timeRange: String, amount: Int): List<Track> {
        return try {
            withContext(dispatcher) {
                userDataRepository.getCurrentUserTopTracks(timeRange, amount)
            }
        } catch (ex: CredentialsExpiredException) {
            throw ex // =)
        } catch (ex: Exception) {
            throw ex
        }
    }
}
