package ru.anura.cryptoapp.presentation

import android.app.Application
import ru.anura.cryptoapp.di.DaggerApplicationComponent

class CoinApp: Application() {
    val component by lazy {
        DaggerApplicationComponent.factory()
            .create( this)
    }

}
