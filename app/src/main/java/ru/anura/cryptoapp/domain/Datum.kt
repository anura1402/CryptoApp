package ru.anura.cryptoapp.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.anura.cryptoapp.domain.CoinInfo

data class Datum (
    @SerializedName("CoinInfo")
    @Expose
    val coinInfo: CoinInfo? = null
)
