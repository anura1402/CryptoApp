package ru.anura.cryptoapp.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinInfo (
    @SerializedName("Name")
    @Expose
    val name: String? = null
)
