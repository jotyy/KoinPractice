package top.jotyy.koinpractice.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import top.jotyy.koinpractice.data.model.User

interface UserService {

    @GET("users/{user}")
    suspend fun fetchUserInfo(@Path("user") name: String): Response<User>
}
