package ru.itis.morefy.core.data.response.track

import ru.itis.morefy.core.data.response.common.Track

data class SavedTrackItem(
    val added_at: String,
    val track: Track
)
