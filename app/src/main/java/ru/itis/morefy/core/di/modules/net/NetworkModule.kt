package ru.itis.morefy.core.di.modules.net

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.itis.morefy.BuildConfig
import ru.itis.morefy.core.data.api.*
import ru.itis.morefy.core.di.qualifiers.AuthorizationInterceptor
import ru.itis.morefy.core.di.qualifiers.ContentTypeInterceptor
import ru.itis.morefy.core.domain.repository.AuthorizationRepository
import javax.inject.Singleton

private const val BASE_URL = "https://api.spotify.com/v1/"

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(
        @ContentTypeInterceptor interceptor1: Interceptor,
        @AuthorizationInterceptor interceptor2: Interceptor,
        authenticator: TokenAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor1)
            .addInterceptor(interceptor2)
            .authenticator(authenticator)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                }
            }
            .build()
    }

    @Provides
    @ContentTypeInterceptor
    @Singleton
    fun provideContentTypeHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val origin = chain.request()
            val newRequest = origin.newBuilder()
                .addHeader("Content-Type", "application/json")
                .build()

            chain.proceed(newRequest)
        }
    }

    @Provides
    @AuthorizationInterceptor
    @Singleton
    fun provideAuthorizationHeaderInterceptor(authenticationRepository: AuthorizationRepository): Interceptor {
        return Interceptor { chain ->
            val origin = chain.request()
            val newRequest = origin.newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer ${authenticationRepository.getTokens().accessToken}"
                )
                .build()

            chain.proceed(newRequest)
        }
    }

    @Provides
    fun provideAlbumsApi(retrofit: Retrofit): SpotifyAlbumsApi {
        return retrofit.create(SpotifyAlbumsApi::class.java)
    }

    @Provides
    fun provideArtistsApi(retrofit: Retrofit): SpotifyArtistsApi {
        return retrofit.create(SpotifyArtistsApi::class.java)
    }

    @Provides
    fun providePlaylistApi(retrofit: Retrofit): SpotifyPlaylistsApi {
        return retrofit.create(SpotifyPlaylistsApi::class.java)
    }

    @Provides
    fun provideRecommendationsApi(retrofit: Retrofit): SpotifyRecommendationsApi {
        return retrofit.create(SpotifyRecommendationsApi::class.java)
    }

    @Provides
    fun provideSearchApi(retrofit: Retrofit): SpotifySearchApi {
        return retrofit.create(SpotifySearchApi::class.java)
    }

    @Provides
    fun provideTracksApi(retrofit: Retrofit): SpotifyTracksApi {
        return retrofit.create(SpotifyTracksApi::class.java)
    }

    @Provides
    fun provideUsersApi(retrofit: Retrofit): SpotifyUsersApi {
        return retrofit.create(SpotifyUsersApi::class.java)
    }

    @Provides
    fun providePlayerApi(retrofit: Retrofit):SpotifyPlayerApi{
        return retrofit.create(SpotifyPlayerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, factory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(factory)
            .build()
    }

    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

}
