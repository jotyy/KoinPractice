package top.jotyy.koinpractice.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import top.jotyy.koinpractice.data.model.User

interface ApiService {

    @GET("users/{user}")
    suspend fun fetchUser(@Path("user") user: String): User
}
