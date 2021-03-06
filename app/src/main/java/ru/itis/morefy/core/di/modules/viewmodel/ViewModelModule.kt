package ru.itis.morefy.core.di.modules.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.itis.morefy.core.presentation.viewmodels.ProfileViewModel
import ru.itis.morefy.core.presentation.viewmodels.TokensViewModel
import ru.itis.morefy.core.presentation.viewmodels.details.ArtistViewModel
import ru.itis.morefy.core.presentation.viewmodels.details.TrackViewModel
import ru.itis.morefy.search.presentation.viewmodel.SearchViewModel
import ru.itis.morefy.statistics.presentation.viewmodel.StatsViewModel

@Module
interface ViewModelModule {

    @Binds
    @[IntoMap ViewModelKey(TokensViewModel::class)]
    fun provideTokensViewModel(vm: TokensViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(StatsViewModel::class)]
    fun provideStatsViewModel(vm: StatsViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(ProfileViewModel::class)]
    fun provideProfileViewModel(vm: ProfileViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(TrackViewModel::class)]
    fun provideTrackViewModel(vm: TrackViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(ArtistViewModel::class)]
    fun provideArtistViewModel(vm: ArtistViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(SearchViewModel::class)]
    fun provideSearchViewModel(vm: SearchViewModel): ViewModel
}
