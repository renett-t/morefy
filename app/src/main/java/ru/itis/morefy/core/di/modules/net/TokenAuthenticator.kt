package ru.itis.morefy.core.di.modules.net

import android.util.Log
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import ru.itis.morefy.core.domain.service.RefreshTokenService
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val refreshTokenService: RefreshTokenService
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        Log.w("EXPIRED CREDENTIALS", "Failed to authenticate requests to api, refreshing token")
        // possibly dangerous synchronous call.....
        refreshTokenService.updateTokens()

        return response.request.newBuilder()
            .removeHeader("Authorization")
            .addHeader("Authorization", "Bearer ${refreshTokenService.getTokens().accessToken}")
            .build()
    }
}
