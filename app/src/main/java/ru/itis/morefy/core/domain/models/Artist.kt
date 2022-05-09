package ru.itis.morefy.core.domain.models

data class Artist (
    private val id: String,

    private val name: String,
    private val followerCount: Int,
    private val genres: List<Genre>,
    private val imageUrl: String,

    private val popularity: Int,

    private val uri: String,
)
