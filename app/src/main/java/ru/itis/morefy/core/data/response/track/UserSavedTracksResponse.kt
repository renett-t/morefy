package ru.itis.morefy.core.data.response.track

data class UserSavedTracksResponse(
    val href: String,
    val items: List<SavedTrackItem>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int
)
