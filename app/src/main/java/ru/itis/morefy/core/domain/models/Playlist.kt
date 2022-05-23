package ru.itis.morefy.core.domain.models

import ru.itis.morefy.core.domain.models.features.AverageTracksFeatures

data class Playlist (
    val id: String,

    val type: String,
    val tracksCount: Int,

    val name: String,
    val imageUrl: String,
    val isCollaborative: Boolean,
    val isPublic: Boolean,
    val followerCount: Int,
    val owner: User,
    val tracks: List<Track>,

    val statistics: AverageTracksFeatures?,

    val uri: String,
) {
    constructor(id: String, type: String, tracksCount: Int, name: String, imageUrl: String, isCollaborative: Boolean, isPublic: Boolean, owner: User, uri: String) :
            this(id, type, tracksCount, name, imageUrl, isCollaborative, isPublic, 0, owner, emptyList(), null, uri)

}
