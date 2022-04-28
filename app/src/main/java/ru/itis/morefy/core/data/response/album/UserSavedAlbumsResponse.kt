package ru.itis.morefy.core.data.response.album

data class UserSavedAlbumsResponse(
    val href: String,
    val items: List<SavedAlbumItem>,
    val limit: Int,
    val offset: Int,
    val next: String?,
    val previous: String?,
    val total: Int
)
