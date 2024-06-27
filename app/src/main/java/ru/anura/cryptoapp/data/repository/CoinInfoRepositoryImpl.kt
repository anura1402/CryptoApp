package ru.anura.cryptoapp.data.repository

import android.app.Application
import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import kotlinx.coroutines.delay
import ru.anura.cryptoapp.data.database.AppDatabase
import ru.anura.cryptoapp.data.mapper.CoinMapper
import ru.anura.cryptoapp.data.network.ApiFactory
import ru.anura.cryptoapp.data.workers.RefreshDataWorker
import ru.anura.cryptoapp.domain.CoinInfo
import ru.anura.cryptoapp.domain.CoinRepository

class CoinInfoRepositoryImpl(private val application: Application) : CoinRepository {
    private val coinInfoDao = AppDatabase.getInstance(application).coinPriceInfoDao()
    private val mapper = CoinMapper()
    override fun getCoinInfoList(): LiveData<List<CoinInfo>> =
        MediatorLiveData<List<CoinInfo>>().apply {
            addSource(coinInfoDao.getPriceList()) { it ->
                value = it.map {
                    mapper.mapDbModelToEntity(it)
                }
                Log.d("CoinRepository", "getCoinInfoList: OK $it")
            }
        }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> =
        MediatorLiveData<CoinInfo>().apply {
            addSource(coinInfoDao.getPriceInfoAboutCoin(fromSymbol)) {
                value = mapper.mapDbModelToEntity(it)
                Log.d("CoinRepository", "getCoinInfo: OK $it")
            }
        }

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(RefreshDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest())
    }

}