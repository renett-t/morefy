package ru.itis.morefy.core.data.tokens.net.response

import ru.itis.morefy.core.domain.models.TokenContainer

class SpotifyTokenResponseMapper {
    fun map(response: SpotifyTokensResponse): TokenContainer {
        return TokenContainer(
            response.access_token,
            response.token_type,
            response.scope,
            response.expires_in,
            response.refresh_token)
    }
}

