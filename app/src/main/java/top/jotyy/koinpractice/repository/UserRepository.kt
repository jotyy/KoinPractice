package top.jotyy.koinpractice.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import top.jotyy.koinpractice.data.Failure
import top.jotyy.koinpractice.data.MyResult
import top.jotyy.koinpractice.data.State
import top.jotyy.koinpractice.data.Success
import top.jotyy.koinpractice.data.local.UserDao
import top.jotyy.koinpractice.data.model.User
import top.jotyy.koinpractice.data.remote.UserService

class UserRepository(
    private val userService: UserService,
    private val userDao: UserDao
) {

    suspend fun fetchUser(name: String) =
        flow {
            emit(State.Loading)

            val apiResponse = userService.fetchUserInfo(name)
            val user = apiResponse.body()

            if (apiResponse.isSuccessful && user != null) {
                userDao.insertUser(user)
            } else {
                emit(Failure(apiResponse.message()))
            }

            emit(Success(userDao.getUserByName(name)))

            emit(State.Loaded)
        }
            .catch {
                emit(Failure(it.message ?: "fetch user failed"))
            }
            .flowOn(Dispatchers.IO)
}
