package ru.anura.cryptoapp.domain

import javax.inject.Inject

class GetCoinInfoUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {

    operator fun invoke(fromSymbol: String) = coinRepository.getCoinInfo(fromSymbol)

}