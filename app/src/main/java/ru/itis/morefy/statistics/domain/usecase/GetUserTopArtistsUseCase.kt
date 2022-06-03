package ru.itis.morefy.statistics.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.repository.UserDataRepository
import javax.inject.Inject

class GetUserTopArtistsUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke (timeRange: String, amount: Int): List<Artist> {
        return try {
            withContext(dispatcher) {
                userDataRepository.getCurrentUserTopArtists(timeRange, amount)
            }
        } catch (ex: Exception) {
            throw ex
        }
    }
}
