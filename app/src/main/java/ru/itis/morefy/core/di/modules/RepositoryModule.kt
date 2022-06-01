package ru.itis.morefy.core.di.modules

import dagger.Binds
import dagger.Module
import ru.itis.morefy.core.data.repository.SpotifyArtistsRepositoryImpl
import ru.itis.morefy.core.data.repository.SpotifyTracksRepositoryImpl
import ru.itis.morefy.core.data.repository.SpotifyUserDataRepositoryImpl
import ru.itis.morefy.core.data.tokens.local.AuthorizationRepositoryImpl
import ru.itis.morefy.core.data.tokens.net.SpotifyTokensRepositoryImpl
import ru.itis.morefy.core.domain.repository.*

@Module
interface RepositoryModule {

    @Binds
    fun provideAuthRepository(authRepositoryImpl: AuthorizationRepositoryImpl): AuthorizationRepository

    @Binds
    fun provideTokensRepository(spotifyTokensRepositoryImpl: SpotifyTokensRepositoryImpl): SpotifyTokensRepository

    @Binds
    fun provideUserDataRepository(repository: SpotifyUserDataRepositoryImpl): UserDataRepository

    @Binds
    fun provideArtistsRepository(impl: SpotifyArtistsRepositoryImpl): ArtistsRepository

    @Binds
    fun provideTracksRepository(impl: SpotifyTracksRepositoryImpl): TracksRepository
}
