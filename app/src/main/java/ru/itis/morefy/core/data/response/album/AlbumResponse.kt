package ru.itis.morefy.core.data.response.album

import ru.itis.morefy.core.data.response.common.ArtistShorted
import ru.itis.morefy.core.data.response.common.Copyright
import ru.itis.morefy.core.data.response.common.Image
import ru.itis.morefy.core.data.response.common.Tracks

data class AlbumResponse (
    val album_type: String,
    val artists: List<ArtistShorted>,
    val copyrights: List<Copyright>,
    val genres: List<Any>,
    val href: String,
    val id: String,
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
