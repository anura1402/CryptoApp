package ru.anura.cryptoapp.domain

class GetCoinInfoListUseCase(private val coinRepository: CoinRepository) {
    operator fun invoke() = coinRepository.getCoinInfoList()

}