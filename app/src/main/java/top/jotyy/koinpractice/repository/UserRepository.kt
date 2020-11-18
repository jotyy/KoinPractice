package top.jotyy.koinpractice.repository

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import top.jotyy.koinpractice.data.Failure
import top.jotyy.koinpractice.data.State
import top.jotyy.koinpractice.data.Success
import top.jotyy.koinpractice.data.local.UserDao
import top.jotyy.koinpractice.data.model.User

class UserRepository(
    private val userClient: HttpClient,
    private val userDao: UserDao
) {

    suspend fun fetchUser(name: String) =
        flow {
            emit(State.Loading)
            // fetch user
            val user = userClient.get<User>("https://api.github.com/users/$name")
            // clear&store user info
            userDao.delete()
            userDao.insertUser(user)
            emit(Success(userDao.getUserByName(name)))
            
            emit(State.Loaded)
        }
            .catch {
                emit(Failure(it.message ?: "fetch latest user failed"))
                emit(Success(userDao.getUserByName(name)))
                emit(State.Loaded)
            }
            .flowOn(Dispatchers.IO)
}
