package ru.anura.cryptoapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.anura.cryptoapp.domain.CoinInfo
import ru.anura.cryptoapp.domain.GetCoinInfoListUseCase
import ru.anura.cryptoapp.domain.GetCoinInfoUseCase
import ru.anura.cryptoapp.domain.LoadDataUseCase
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    private val getCoinInfoUseCase: GetCoinInfoUseCase,
    private val getCoinInfoListUseCase: GetCoinInfoListUseCase,
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {

    val coinInfoList: LiveData<List<CoinInfo>> = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)


    init {
        loadDataUseCase()
    }
}