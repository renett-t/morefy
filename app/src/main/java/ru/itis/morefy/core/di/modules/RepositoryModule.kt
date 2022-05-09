package ru.itis.morefy.core.di.modules

import dagger.Binds
import dagger.Module
import ru.itis.morefy.core.data.tokens.local.AuthorizationRepositoryImpl
import ru.itis.morefy.core.data.tokens.net.SpotifyTokensRepositoryImpl
import ru.itis.morefy.core.domain.repository.AuthorizationRepository
import ru.itis.morefy.core.domain.repository.SpotifyTokensRepository

@Module
interface RepositoryModule {

    @Binds
    fun provideAuthRepository(authRepositoryImpl: AuthorizationRepositoryImpl): AuthorizationRepository

    @Binds
    fun provideTokensRepository(spotifyTokensRepositoryImpl: SpotifyTokensRepositoryImpl): SpotifyTokensRepository
}
