package top.jotyy.koinpractice.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import top.jotyy.koinpractice.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE name = :name")
    fun getUserByName(name: String): User

    @Insert
    fun insertUser(user: User)

    @Query("DELETE FROM user")
    fun delete()
}
