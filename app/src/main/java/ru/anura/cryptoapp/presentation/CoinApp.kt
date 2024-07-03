package ru.anura.cryptoapp.presentation

import android.app.Application
import androidx.work.Configuration
import ru.anura.cryptoapp.data.database.AppDatabase
import ru.anura.cryptoapp.data.mapper.CoinMapper
import ru.anura.cryptoapp.data.network.ApiFactory
import ru.anura.cryptoapp.data.workers.RefreshDataWorkerFactory

import ru.anura.cryptoapp.di.DaggerApplicationComponent

class CoinApp : Application(), Configuration.Provider {
    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(
                RefreshDataWorkerFactory(
                    AppDatabase.getInstance(this).coinPriceInfoDao(),
                    CoinMapper(),
                    ApiFactory.apiService
                )
            ).build()

}
