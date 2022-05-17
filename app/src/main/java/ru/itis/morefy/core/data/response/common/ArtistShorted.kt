package ru.itis.morefy.core.data.response.common

data class ArtistShorted(
    val external_urls: ExternalUrls,
    val href: String,
    val id: String,
    val name: String,
    val type: String,
    val uri: String
)
