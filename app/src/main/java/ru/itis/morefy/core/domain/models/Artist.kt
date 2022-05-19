package ru.itis.morefy.core.domain.models

data class Artist (
    val id: String,

    val name: String,
    val followerCount: Int,
    val genres: List<Genre>?,
    val imageUrl: String?,

    val popularity: Int,

    val uri: String,
)
