package top.jotyy.koinpractice.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import top.jotyy.koinpractice.R
import top.jotyy.koinpractice.data.local.AppDatabase

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            androidApplication().getString(R.string.database)
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<AppDatabase>().userDao()
    }
}
