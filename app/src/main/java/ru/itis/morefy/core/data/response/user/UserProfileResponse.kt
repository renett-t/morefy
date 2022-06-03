package ru.itis.morefy.core.data.response.user

import ru.itis.morefy.core.data.response.common.ExternalUrls
import ru.itis.morefy.core.data.response.common.Followers
import ru.itis.morefy.core.data.response.common.Image

data class UserProfileResponse(
    val display_name: String,
    val external_urls: ExternalUrls,
    val followers: Followers,
    val href: String,
    val id: String,
    val images: List<Image>,
    val type: String,
    val uri: String
)
