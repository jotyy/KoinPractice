package top.jotyy.koinpractice.repository

import android.util.Log
import top.jotyy.koinpractice.data.Failure
import top.jotyy.koinpractice.data.HttpResult
import top.jotyy.koinpractice.data.Success
import top.jotyy.koinpractice.data.model.User
import top.jotyy.koinpractice.data.remote.ApiService
import java.io.IOException

class UserRepository(
    private val api: ApiService
) {

    suspend fun fetchUser(user: String): HttpResult<User> =
        try {
            val result = api.fetchUser(user)
            Log.w("HTTP", result.toString())
            Success(result)
        } catch (e: IOException) {
            Failure(e.message ?: "Network error")
        }
}
