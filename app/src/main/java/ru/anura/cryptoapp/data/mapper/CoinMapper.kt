package ru.anura.cryptoapp.data.mapper

import com.google.gson.Gson
import ru.anura.cryptoapp.data.database.CoinInfoDBModel
import ru.anura.cryptoapp.data.network.model.CoinInfoDto
import ru.anura.cryptoapp.data.network.model.CoinInfoJSONContainerDto
import ru.anura.cryptoapp.data.network.model.CoinNamesListDto
import ru.anura.cryptoapp.domain.CoinInfo
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class CoinMapper @Inject constructor(){
    fun mapDtoToDbModel(coinInfoDto: CoinInfoDto): CoinInfoDBModel {
        return CoinInfoDBModel(
            fromSymbol = coinInfoDto.fromSymbol,
            toSymbol = coinInfoDto.toSymbol,
            price = coinInfoDto.price,
            lastUpdate = coinInfoDto.lastUpdate,
            highDay = coinInfoDto.highDay,
            lowDay = coinInfoDto.lowDay,
            lastMarket = coinInfoDto.lastMarket,
            imageUrl = BASE_IMAGE_URL+ coinInfoDto.imageUrl
        )
    }

    fun mapJsonContainerToList(jsonContainer: CoinInfoJSONContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainer.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapCoinNamesListToString(namesList: CoinNamesListDto): String {
        return namesList.names?.map {
            it.coinName?.name
        }?.joinToString(",") ?: ""
    }

    fun mapDbModelToEntity(dbModel: CoinInfoDBModel): CoinInfo {
        return CoinInfo(
            fromSymbol = dbModel.fromSymbol,
            toSymbol = dbModel.toSymbol,
            price = dbModel.price,
            lastUpdate = convertTimestampToTime(dbModel.lastUpdate),
            highDay = dbModel.highDay,
            lowDay = dbModel.lowDay,
            lastMarket = dbModel.lastMarket,
            imageUrl = dbModel.imageUrl
        )
    }

    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }
    companion object{
        const val BASE_IMAGE_URL = "https://cryptocompare.com"
    }
}