package top.jotyy.koinpractice.repository

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import top.jotyy.koinpractice.MainCoroutinesRule
import top.jotyy.koinpractice.data.State
import top.jotyy.koinpractice.data.Success
import top.jotyy.koinpractice.data.local.UserDao
import top.jotyy.koinpractice.data.remote.UserService
import top.jotyy.koinpractice.mockUserInfo

class UserRepositoryTest {
    private lateinit var repository: UserRepository
    private val userService: UserService = mock()
    private val userDao: UserDao = mock()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()


    @Before
    fun setup() {
        repository = UserRepository(userService, userDao)
    }

    @Test
    fun fetchUserInfoTest() = runBlocking {
        val mockData = mockUserInfo()
        // set mock data
        whenever(userDao.getUserByName(any())).thenReturn(mockData)
        whenever(userService.fetchUserInfo(any()))
            .thenReturn(Response.success(mockData))

        val flow = repository.fetchUser("jotyy")
        flow.collectIndexed { index, value ->
            when (index) {
                0 -> assertThat(value, `is`(State.Loading))
                1 -> assertThat((value as Success).data.toString(), `is`(mockData.toString()))
                2 -> assertThat(value, `is`(State.Loaded))
            }
        }
    }
}
