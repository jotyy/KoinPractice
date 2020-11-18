package top.jotyy.koinpractice.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import top.jotyy.koinpractice.data.model.User

@Database(entities = [User::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}
