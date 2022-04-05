package ru.itis.morefy.core.data.tokens.net.response

import ru.itis.morefy.core.domain.models.TokenContainer

class TokenResponseMapper {
    fun map(response: SpotifyTokensResponse): TokenContainer {
        return TokenContainer(
            response.accessToken,
            response.tokenType,
            response.scope,
            response.expiresIn,
            response.refreshToken)
    }
}

