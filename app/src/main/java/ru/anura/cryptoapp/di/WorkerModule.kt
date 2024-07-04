package ru.anura.cryptoapp.di

import androidx.work.ListenableWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.anura.cryptoapp.data.workers.ChildWorkerFactory
import ru.anura.cryptoapp.data.workers.RefreshDataWorker

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(RefreshDataWorker::class)
    fun bindRefreshDataWorkerFactory(worker: RefreshDataWorker.Factory):ChildWorkerFactory
}