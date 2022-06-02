package ru.itis.morefy.core.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.morefy.core.domain.models.Track
import ru.itis.morefy.core.domain.models.features.TrackFeatures
import ru.itis.morefy.core.domain.repository.TracksRepository
import javax.inject.Inject

class GetTrackUseCase @Inject constructor(
    private val tracksRepository: TracksRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(id: String): Track {
        return try {
            withContext(dispatcher) {
                tracksRepository.getTrackById(id)
            }
        } catch (ex: Exception) {
            throw ex
        }
    }

    suspend fun getMultiple(ids: List<String>): List<Track> {
        return try {
            withContext(dispatcher) {
                tracksRepository.getTracksByIds(ids)
            }
        } catch (ex: Exception) {
            throw ex
        }
    }

    suspend fun getTrackFeatures(id: String): TrackFeatures {
        return try {
            withContext(dispatcher) {
                tracksRepository.getTrackFeatures(id)
            }
        } catch (ex: Exception) {
            throw ex
        }
    }
}
