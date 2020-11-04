package top.jotyy.koinpractice.di

import org.koin.dsl.module
import top.jotyy.koinpractice.repository.UserRepository

val repositoryModule = module {
    factory {
        UserRepository(get())
    }
}
