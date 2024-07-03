package ru.anura.cryptoapp.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.anura.cryptoapp.data.database.AppDatabase
import ru.anura.cryptoapp.data.database.CoinInfoDao
import ru.anura.cryptoapp.data.network.ApiFactory
import ru.anura.cryptoapp.data.network.ApiService
import ru.anura.cryptoapp.data.repository.CoinRepositoryImpl
import ru.anura.cryptoapp.domain.CoinRepository

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    companion object{
        @Provides
        @ApplicationScope
        fun provideCoinInfoDao(
            application: Application
        ): CoinInfoDao{
            return AppDatabase.getInstance(application).coinPriceInfoDao()
        }

        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService{
            return ApiFactory.apiService
        }
    }
}