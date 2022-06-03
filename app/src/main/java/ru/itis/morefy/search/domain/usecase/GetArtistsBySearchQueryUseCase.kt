package ru.itis.morefy.search.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.morefy.core.domain.models.Artist
import ru.itis.morefy.search.domain.repository.SearchQueryRepository
import javax.inject.Inject

class GetArtistsBySearchQueryUseCase @Inject constructor(
    private val searchQueryRepository: SearchQueryRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(query: String, amount: Int): List<Artist> {
        return try {
            withContext(dispatcher) {
                searchQueryRepository.getArtistsByQuery(query, amount)
            }
        } catch (ex: Exception) {
            throw ex
        }
    }
}
