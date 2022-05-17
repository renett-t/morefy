package ru.itis.morefy.core.data.repository

import ru.itis.morefy.core.data.api.SpotifyPlaylistsApi
import ru.itis.morefy.core.data.mapper.SpotifyEntitiesMapper
import ru.itis.morefy.core.domain.repository.SpotifyRepository
import javax.inject.Inject

private const val BASE_URL = "https://api.spotify.com/v1/"

class SpotifyRepositoryWebImpl @Inject constructor(
    private val mapper: SpotifyEntitiesMapper,
    private val playlistsApi: SpotifyPlaylistsApi, // todo: add all api? или также разделить на несколько репозиториев
) : SpotifyRepository {

}
