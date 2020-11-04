package top.jotyy.koinpractice.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import top.jotyy.koinpractice.MainViewModel

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}
