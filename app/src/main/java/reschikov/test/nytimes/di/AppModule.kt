package reschikov.test.nytimes.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import reschikov.test.nytimes.data.IRequester
import reschikov.test.nytimes.data.Repository
import reschikov.test.nytimes.data.network.NetWorkProvider
import reschikov.test.nytimes.ui.screens.IStoring
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindIRequester(netWorkProvider: NetWorkProvider) : IRequester

    @Binds
    @Singleton
    abstract fun bindRepository(repository: Repository) : IStoring
}