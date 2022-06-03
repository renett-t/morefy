package ru.itis.morefy.core.data.response.playlist

import ru.itis.morefy.core.data.response.common.ExternalUrls

data class AddedBy(
    val external_urls: ExternalUrls,
    val href: String,
    val id: String,
    val type: String,
    val uri: String
)
