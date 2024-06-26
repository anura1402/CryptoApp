package ru.anura.cryptoapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import ru.anura.cryptoapp.domain.CoinInfo

class CoinInfoDiffCallback: DiffUtil.ItemCallback<CoinInfo>() {
    override fun areItemsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem.fromSymbol==newItem.fromSymbol
    }

    override fun areContentsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem.price==newItem.price
    }
}