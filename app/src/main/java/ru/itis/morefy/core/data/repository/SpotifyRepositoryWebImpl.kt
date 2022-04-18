package ru.itis.morefy.core.data.repository

import ru.itis.morefy.core.data.api.SpotifyApi
import ru.itis.morefy.core.data.mapper.SpotifyEntitiesMapper
import ru.itis.morefy.core.domain.repository.SpotifyRepository
import javax.inject.Inject

const val URL = "https://api.spotify.com/v1/"

class SpotifyRepositoryWebImpl @Inject constructor(
    private val mapper: SpotifyEntitiesMapper,
    private val api: SpotifyApi,
) : SpotifyRepository {

}
