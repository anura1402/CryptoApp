package ru.anura.cryptoapp.domain

import javax.inject.Inject

class GetCoinInfoListUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
    operator fun invoke() = coinRepository.getCoinInfoList()

}