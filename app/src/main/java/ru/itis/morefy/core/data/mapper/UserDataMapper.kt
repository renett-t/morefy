package ru.itis.morefy.core.data.mapper

import ru.itis.morefy.core.data.response.user.CurrentUserProfileResponse
import ru.itis.morefy.core.data.response.user.UserProfileResponse
import ru.itis.morefy.core.domain.models.User
import javax.inject.Inject

class UserDataMapper @Inject constructor() {

    fun mapFrom(resp: CurrentUserProfileResponse): User {
        var image:String? = null
        if (resp.images.isNotEmpty()) {
            image = resp.images.last().url
        }
        return User(
            resp.id,
            resp.display_name,
            resp.followers.total,
            resp.uri,
            resp.country,
            resp.email,
            image,
            resp.product
        )
    }

    fun mapFrom(resp: UserProfileResponse): User {
        var image:String? = null
        if (resp.images.isNotEmpty()) {
            image = resp.images.last().url
        }
        return User(
            resp.id,
            resp.display_name,
            resp.followers.total,
            resp.uri,
            "country",
            "email",
            image,
            "subscription"
        )
    }
}
