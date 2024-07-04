package ru.anura.cryptoapp.data.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import ru.anura.cryptoapp.data.database.AppDatabase
import ru.anura.cryptoapp.data.database.CoinInfoDao
import ru.anura.cryptoapp.data.mapper.CoinMapper
import ru.anura.cryptoapp.data.network.ApiFactory
import ru.anura.cryptoapp.data.network.ApiService
import javax.inject.Inject

class RefreshDataWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val coinInfoDao: CoinInfoDao,
    private val mapper: CoinMapper,
    private val apiService: ApiService
) : CoroutineWorker(context, workerParams) {


    override suspend fun doWork(): Result {
        while (true) {
            try {
                val topCoins = apiService.getTopCoinsInfo(limit = 50)
                val fromSymbols = mapper.mapCoinNamesListToString(topCoins)
                val jsonContainer = apiService.getFullPriceList(fSyms = fromSymbols)
                val coinInfoDtoList = mapper.mapJsonContainerToList(jsonContainer)
                val coinInfoDBModel = coinInfoDtoList.map {
                    mapper.mapDtoToDbModel(it)
                }
                coinInfoDao.insertPriceList(coinInfoDBModel)
                Log.d("CoinRepository", "loadData: OK")
            } catch (e: Exception) {
            }
            delay(10000)
        }
    }
    companion object{
        const val NAME="RefreshDataWorker"

        fun makeRequest() = OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
    }

    class Factory @Inject constructor(
        private val coinInfoDao: CoinInfoDao,
        private val mapper: CoinMapper,
        private val apiService: ApiService
    ):ChildWorkerFactory{
        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return RefreshDataWorker(
                context,
                workerParameters,
                coinInfoDao,
                mapper,
                apiService
            )
        }

    }
}