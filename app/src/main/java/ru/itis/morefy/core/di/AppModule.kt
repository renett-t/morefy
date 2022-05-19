package ru.itis.morefy.core.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.itis.morefy.core.di.modules.net.NetworkModule
import ru.itis.morefy.core.di.modules.RepositoryModule
import ru.itis.morefy.core.di.modules.viewmodel.ViewModelModule

@Module(
    includes = [
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
class AppModule {

    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}
