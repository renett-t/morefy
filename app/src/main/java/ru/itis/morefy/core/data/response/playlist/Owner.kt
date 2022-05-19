package ru.itis.morefy.core.data.response.playlist

import ru.itis.morefy.core.data.response.common.ExternalUrls

data class Owner(
    val display_name: String,
    val external_urls: ExternalUrls,
    val href: String,
    val id: String,
    val type: String,
    val uri: String
)
