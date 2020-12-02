package top.jotyy.koinpractice.repository

import com.google.gson.Gson
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.json.*
import io.ktor.http.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.flow.collectIndexed
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.internal.runners.statements.Fail
import top.jotyy.koinpractice.data.Failure
import top.jotyy.koinpractice.data.State
import top.jotyy.koinpractice.data.Success
import top.jotyy.koinpractice.data.local.UserDao
import top.jotyy.koinpractice.mockUserInfo


class UserRepositoryTest : BehaviorSpec({
    val userClient: HttpClient = HttpClient(MockEngine) {
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
    val userDao: UserDao = spyk()
    every { userDao.delete() } returns Unit
    every { userDao.getUserByName(any()) } returns mockUserInfo()

    val userRepository = UserRepository(userClient, userDao)

    given("a github user name") {
        `when`("the name is correct") {
            val flow = userRepository.fetchUser("jotyy")
            flow.collectIndexed { index, value ->
                when (index) {
                    0 -> value shouldBeInstanceOf State.Loading::class.java
                    1 -> {
                        value shouldBeInstanceOf Success::class.java
                        (value as Success).data.toString() shouldBeEqualTo mockUserInfo().toString()
                    }
                    2 -> value shouldBeInstanceOf State.Loaded::class.java
                }
            }
        }

        `when`("the name is error") {
            val flow = userRepository.fetchUser("xixi")
            flow.collectIndexed { index, value ->
                when (index) {
                    0 -> value shouldBeInstanceOf State.Loading::class.java
                    1 -> value shouldBeInstanceOf Failure::class.java
                    2 -> {
                        value shouldBeInstanceOf Success::class.java
                        (value as Success).data.toString() shouldBeEqualTo mockUserInfo().toString()
                    }
                    3 -> value shouldBeInstanceOf State.Loaded::class.java
                }
            }
        }
    }
})