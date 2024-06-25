package ru.anura.cryptoapp.domain.json

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.anura.cryptoapp.domain.json.CoinData

data class CoinInfoListOfData (
    @SerializedName("Data")
    @Expose
    val data: List<CoinData>? = null
)
