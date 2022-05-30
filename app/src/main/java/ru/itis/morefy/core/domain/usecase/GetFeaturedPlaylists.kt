package ru.itis.morefy.core.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.morefy.core.domain.models.Playlist
import ru.itis.morefy.core.domain.repository.UserDataRepository
import javax.inject.Inject

class GetFeaturedPlaylists @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(): List<Playlist> {
        return try {
            withContext(dispatcher) {
                userDataRepository.getFeaturedPlaylists()
            }
        } catch (ex: Exception) {
            throw ex
        }
    }
}