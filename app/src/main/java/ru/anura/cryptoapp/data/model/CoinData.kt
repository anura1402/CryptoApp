package ru.anura.cryptoapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinData (
    @SerializedName("CoinInfo")
    @Expose
    val coinInfo: CoinInfo? = null
)
