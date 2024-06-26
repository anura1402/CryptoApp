package ru.anura.cryptoapp.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ru.anura.cryptoapp.data.network.ApiFactory
import ru.anura.cryptoapp.data.database.AppDatabase
import ru.anura.cryptoapp.data.network.model.CoinInfoDto
import ru.anura.cryptoapp.data.network.model.CoinInfoJSONContainerDto
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import ru.anura.cryptoapp.data.repository.CoinInfoRepositoryImpl
import ru.anura.cryptoapp.domain.CoinInfo
import ru.anura.cryptoapp.domain.CoinRepository
import ru.anura.cryptoapp.domain.GetCoinInfoListUseCase
import ru.anura.cryptoapp.domain.GetCoinInfoUseCase
import ru.anura.cryptoapp.domain.LoadDataUseCase
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinInfoRepositoryImpl(application)


    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)


    val coinInfoList: LiveData<List<CoinInfo>> = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)


    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }
}