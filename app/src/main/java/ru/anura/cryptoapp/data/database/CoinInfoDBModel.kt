package ru.anura.cryptoapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.anura.cryptoapp.data.network.ApiFactory.BASE_IMAGE_URL
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.anura.cryptoapp.domain.convertTimestampToTime

@Entity(tableName = "full_price_list")
data class CoinInfoDBModel(
    @PrimaryKey
    val fromSymbol: String,
    val toSymbol: String?,
    val price: String?,
    val lastUpdate: Long?,
    val highDay: String?,
    val lowDay: String?,
    val lastMarket: String?,
    val imageUrl: String?
)