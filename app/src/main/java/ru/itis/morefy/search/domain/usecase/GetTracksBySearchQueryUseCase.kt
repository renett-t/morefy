package ru.itis.morefy.search.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.search.domain.repository.SearchQueryRepository
import javax.inject.Inject

class GetTracksBySearchQueryUseCase @Inject constructor(
    private val searchQueryRepository: SearchQueryRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(query: String, amount: Int): List<Track> {
        return try {
            withContext(dispatcher) {
                searchQueryRepository.getTracksByQuery(query, amount)
            }
        } catch (ex: Exception) {
            throw ex
        }
    }
}
