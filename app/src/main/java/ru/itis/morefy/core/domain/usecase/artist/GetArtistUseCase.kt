package ru.itis.morefy.core.domain.usecase.artist

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.repository.ArtistsRepository
import javax.inject.Inject

class GetArtistUseCase @Inject constructor(
    private val artistsRepository: ArtistsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(id: String): Artist {
        return try {
            withContext(dispatcher) {
                artistsRepository.getArtistById(id)
            }
        } catch (ex: Exception) {
            throw ex
        }
    }

    suspend fun getMultiple(ids: List<String>): List<Artist> {
        return try {
            withContext(dispatcher) {
                artistsRepository.getArtistsByIds(ids)
            }
        } catch (ex: Exception) {
            throw ex
        }
    }
}
