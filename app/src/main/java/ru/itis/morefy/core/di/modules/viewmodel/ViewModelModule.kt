package ru.itis.morefy.core.di.modules.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.itis.morefy.core.presentation.viewmodels.TokensViewModel

@Module
interface ViewModelModule {

    @Binds
    @[IntoMap ViewModelKey(TokensViewModel::class)]
    fun provideTokensViewModel(vm: TokensViewModel): ViewModel
}
