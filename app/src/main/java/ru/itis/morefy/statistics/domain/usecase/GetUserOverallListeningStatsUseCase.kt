package ru.itis.morefy.statistics.domain.usecase

import ru.itis.morefy.statistics.domain.models.OverallListeningStats
import javax.inject.Inject

class GetUserOverallListeningStatsUseCase @Inject constructor(

) {
    operator fun invoke (timeRange: String): OverallListeningStats {
        // todo
        return OverallListeningStats(null, null, null, null)
    }
}
