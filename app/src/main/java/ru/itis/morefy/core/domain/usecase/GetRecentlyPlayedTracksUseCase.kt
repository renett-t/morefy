package ru.itis.morefy.core.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.core.domain.repository.UserDataRepository
import javax.inject.Inject

class GetRecentlyPlayedTracksUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke (): List<Track> {
        return try {
            withContext(dispatcher) {
                userDataRepository.getRecentlyPlayedTracks()
            }
        } catch (ex: Exception) {
            throw ex
        }
    }
}