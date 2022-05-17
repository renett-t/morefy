package ru.itis.morefy.core.data.response.common

import ru.itis.morefy.core.data.response.common.ExternalUrls
import ru.itis.morefy.core.data.response.common.Followers
import ru.itis.morefy.core.data.response.common.Image

data class ArtistResponse (
    val external_urls: ExternalUrls,
    val followers: Followers,
    val genres: List<String>,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val popularity: Int,
    val type: String,
    val uri: String
)
