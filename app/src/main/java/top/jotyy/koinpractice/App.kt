package top.jotyy.koinpractice

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import top.jotyy.koinpractice.di.apiModule
import top.jotyy.koinpractice.di.databaseModule
import top.jotyy.koinpractice.di.repositoryModule
import top.jotyy.koinpractice.di.viewModelModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(apiModule)
            modules(databaseModule)
            modules(repositoryModule)
            modules(viewModelModule)
        }
    }
}
