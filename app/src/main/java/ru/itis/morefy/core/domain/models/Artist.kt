package ru.itis.morefy.core.domain.models

data class Artist(
    val id: String,

    val name: String,
    val followerCount: Int,
    val genres: List<Genre>?,
    val imageUrl: String?,

    val popularity: Int,

    val uri: String,
) {
    constructor(id: String, name: String, uri: String)
            : this(id, name, -1, null, null, -1, uri)
}
