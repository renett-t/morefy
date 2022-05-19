package ru.itis.morefy.core.data.mapper

import android.util.Log
import ru.itis.morefy.core.data.response.user.CurrentUserProfileResponse
import ru.itis.morefy.core.domain.models.User
import javax.inject.Inject

class UserDataMapper @Inject constructor() {

    fun mapFrom(resp: CurrentUserProfileResponse): User {
        Log.e("USER DATA MAP", "MAPPING RESULT")
        return User(
            resp.id,
            resp.display_name,
            resp.followers.total,
            resp.uri,
            resp.country,
            resp.email,
            resp.images.last().url,
            resp.product
        )
    }
}
