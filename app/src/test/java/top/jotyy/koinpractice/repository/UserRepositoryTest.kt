package top.jotyy.koinpractice.repository

import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.json.*
import io.ktor.http.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import top.jotyy.koinpractice.MainCoroutinesRule
import top.jotyy.koinpractice.data.Failure
import top.jotyy.koinpractice.data.State
import top.jotyy.koinpractice.data.Success
import top.jotyy.koinpractice.data.local.UserDao
import top.jotyy.koinpractice.mockUserInfo

class UserRepositoryTest {
    private lateinit var repository: UserRepository
    private val userClient: HttpClient = HttpClient(MockEngine) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }

        engine {
            addHandler { request ->
                when (request.url.fullPath) {
                    "/users/jotyy" -> {
                        val responseHeaders =
                            headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                        respond(Gson().toJson(mockUserInfo()), headers = responseHeaders)
                    }
                    else -> error("Unhandled ${request.url.fullPath}")
                }
            }
        }
    }
    private val userDao: UserDao = mock()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()


    @Before
    fun setup() {
        repository = UserRepository(userClient, userDao)
    }

    @Test
    fun fetchUserInfoSuccess() = runBlocking {
        val mockData = mockUserInfo()
        // set mock data
        whenever(userDao.getUserByName(any())).thenReturn(mockData)

        val flow = repository.fetchUser("jotyy")
        flow.collectIndexed { index, value ->
            when (index) {
                0 -> assertThat(value, `is`(State.Loading))
                1 -> assertThat((value as Success).data.toString(), `is`(mockData.toString()))
                2 -> assertThat(value, `is`(State.Loaded))
            }
        }
    }

    @Test
    fun fetchUserInfoFailure() = runBlocking {
        val mockData = mockUserInfo()
        // set mock data
        whenever(userDao.getUserByName(any())).thenReturn(mockData)

        val flow = repository.fetchUser("xixi")
        flow.collectIndexed { index, value ->
            when (index) {
                0 -> assertThat(value, `is`(State.Loading))
                1 -> assertThat(value, instanceOf(Failure::class.java))
                2 -> assertThat(value, instanceOf(Success::class.java))
                3 -> assertThat(value, `is`(State.Loaded))
            }
        }
    }
}
