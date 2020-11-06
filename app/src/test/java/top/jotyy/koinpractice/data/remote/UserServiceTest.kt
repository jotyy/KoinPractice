package top.jotyy.koinpractice.data.remote

import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test

class UserServiceTest : ApiAbstract<UserService>() {

    private lateinit var service: UserService

    @Before
    fun initService() {
        service = createService(UserService::class.java)
    }

    @Test
    fun fetchUserInfoTest() = runBlocking {
        enqueueResponse("/user.json")
        val response = service.fetchUserInfo("jotyy")
        val responseBody = requireNotNull(response.body())
        mockWebServer.takeRequest()

        assertThat(responseBody.name, `is`("jotyy"))
    }
}
