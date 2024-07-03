package ru.anura.cryptoapp.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.anura.cryptoapp.data.database.AppDatabase
import ru.anura.cryptoapp.data.database.CoinInfoDao
import ru.anura.cryptoapp.data.network.ApiService
import ru.anura.cryptoapp.data.repository.CoinRepositoryImpl
import ru.anura.cryptoapp.domain.CoinRepository

@Module
interface DataModule {

    @Binds
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    companion object{
        @Provides
        fun provideCoinInfoDao(
            application: Application
        ): CoinInfoDao{
            return AppDatabase.getInstance(application).coinPriceInfoDao()
        }
    }
}