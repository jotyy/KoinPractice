package top.jotyy.koinpractice.di

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import org.koin.dsl.module

val apiModule = module {
    single {
        HttpClient(Android) {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }
    }
}
