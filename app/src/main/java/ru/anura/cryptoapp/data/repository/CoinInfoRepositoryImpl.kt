package ru.anura.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import kotlinx.coroutines.delay
import ru.anura.cryptoapp.data.database.AppDatabase
import ru.anura.cryptoapp.data.mapper.CoinMapper
import ru.anura.cryptoapp.data.network.ApiFactory
import ru.anura.cryptoapp.domain.CoinInfo
import ru.anura.cryptoapp.domain.CoinRepository

class CoinInfoRepositoryImpl(application: Application):CoinRepository {
    private val coinInfoDao = AppDatabase.getInstance(application).coinPriceInfoDao()
    private val mapper = CoinMapper()
    private val apiService = ApiFactory.apiService
    override fun getCoinInfoList(): LiveData<List<CoinInfo>> =MediatorLiveData<List<CoinInfo>>().apply {
        addSource(coinInfoDao.getPriceList()){
            value = it.map{
                mapper.mapDbModelToEntity(it)
            }
    }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> =MediatorLiveData<CoinInfo>().apply {
        addSource(coinInfoDao.getPriceInfoAboutCoin(fromSymbol)){
            value = mapper.mapDbModelToEntity(it)
        }
    }

    override suspend fun loadData() {
        while (true) {
            val topCoins = apiService.getTopCoinsInfo(limit = 50)
            val fromSymbols = mapper.mapCoinNamesListToString(topCoins)
            val jsonContainer = apiService.getFullPriceList(fSyms = fromSymbols)
            val coinInfoDtoList = mapper.mapJsonContainerToList(jsonContainer)
            val coinInfoDBModel = coinInfoDtoList.map {
                mapper.mapDtoToDbModel(it)
            }
            coinInfoDao.insertPriceList(coinInfoDBModel)
            delay(10000)
        }
    }


}