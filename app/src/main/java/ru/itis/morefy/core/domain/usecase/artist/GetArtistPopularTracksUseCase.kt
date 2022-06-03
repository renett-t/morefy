package ru.itis.morefy.core.domain.usecase.artist

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.core.domain.repository.ArtistsRepository
import javax.inject.Inject

class GetArtistPopularTracksUseCase @Inject constructor(
    private val artistsRepository: ArtistsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(id: String): List<Track> {
        return try {
            withContext(dispatcher) {
                artistsRepository.getArtistPopularTracks(id)
            }
        } catch (ex: Exception) {
            throw ex
        }
    }
}
