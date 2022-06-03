package ru.itis.morefy.core.domain.usecase.artist

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.core.domain.repository.ArtistsRepository
import javax.inject.Inject

class GetSimilarArtistsUseCase @Inject constructor(
    private val artistsRepository: ArtistsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(id: String): List<Artist> {
        return try {
            withContext(dispatcher) {
                artistsRepository.getSimilarArtists(id)
            }
        } catch (ex: Exception) {
            throw ex
        }
    }
}
