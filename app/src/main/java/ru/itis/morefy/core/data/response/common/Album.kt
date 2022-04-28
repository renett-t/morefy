package ru.itis.morefy.core.data.response.common

data class Album(
    val id: String,
    val album_type: String,
    val artists: List<ArtistShorted>,
    val copyrights: List<Copyright>,
    val genres: List<String>?,
    val href: String,
    val images: List<Image>,
    val label: String,
    val name: String,
    val popularity: Int,
    val release_date: String,
    val release_date_precision: String,
    val total_tracks: Int,
    val tracks: Tracks,
    val uri: String
)
