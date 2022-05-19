package ru.itis.morefy.core.di.modules

import android.util.Log
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
    fun provideHttpClient(@ContentTypeInterceptor interceptor1: Interceptor, @AuthorizationInterceptor interceptor2: Interceptor): OkHttpClient {
        Log.e("PROVIDING DEPENDENCIES", "OK HTTP CLIENT")
        return OkHttpClient.Builder()
            .addInterceptor(interceptor1)
            .addInterceptor(interceptor2)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                }
            }
            .build()
    }

    @Provides
    @ContentTypeInterceptor
    @Singleton
    fun provideContentTypeHeaderInterceptor(authenticationRepository: AuthorizationRepository): Interceptor {
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
        Log.e("PROVIDING DEPENDENCIES", "AUTH HEADER INTERCEPTOR")
        Log.e("TOKEN", authenticationRepository.getTokens().accessToken)
        // accessToken=BQDIBavAwgFeWsUlRX7BvvVERDUwCsI4sNlTidJnUvqK3doG45UvkJU1s-qjTcV9BLnfszjDs50CzThB9Yi6zTow8NrFTfaJQ2Pxu7cQMTJke62oNWdf2KM6bWWdcjUxeOX_DsRbwXea92agaVA9WBL3RlH37hB2LmzpEgOlqOzYqexDf3xQNsxRvaGJ18f7WVg2QdLW_TymjA4Sl4kiMpTt-WN-_5BJETojXftxtZJONqGxcawE2ccRfNc06D2Zb6G97uqm3OznYaLz0ncH5yMAkuB7wOq9eTR_7wAs, tokenType=Bearer, scope=playlist-read-private playlist-read-collaborative user-follow-read playlist-modify-private user-read-email user-read-private app-remote-control user-library-read user-library-modify playlist-modify-public user-read-playback-state user-read-recently-played user-top-read, expiresIn=3600, refreshToken=AQCgyRHnISJOxIr2LSZWX4RVFt7kwXEwLOJo98im9mk_2og2ZeEt-ctdWpBgdVuAgXelUEyGle7xcwK9w4NLasbMEsQ71pY6jBSp21p__vLOERjLS5cgfNSaEDxXbHe4sLI)
        return Interceptor { chain ->
            val origin = chain.request()
            val newRequest = origin.newBuilder()
                .addHeader("Authorization", "Bearer ${authenticationRepository.getTokens().accessToken}")
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
