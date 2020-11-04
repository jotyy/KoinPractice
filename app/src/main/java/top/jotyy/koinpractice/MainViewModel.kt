package top.jotyy.koinpractice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import top.jotyy.koinpractice.data.Failure
import top.jotyy.koinpractice.data.State
import top.jotyy.koinpractice.data.Success
import top.jotyy.koinpractice.data.model.User
import top.jotyy.koinpractice.repository.UserRepository

class MainViewModel (
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    private val _error = MutableLiveData<String>()
    private val _state = MutableLiveData<State>()

    val user: LiveData<User> get() = _user
    val error: LiveData<String> get() = _error
    val state: LiveData<State> get() = _state

    init {
        fetchUser()
    }

    fun fetchUser() {
        viewModelScope.launch {
            flow {
                emit(State.Loading)
                emit(userRepository.fetchUser("jotyy"))
                emit(State.Loaded)
            }.collect { result ->
                when (result) {
                    is Success -> _user.value = result.data
                    is Failure -> _error.value = result.error
                    is State.Loading -> _state.value = result
                    is State.Loaded -> _state.value = result
                }
            }
        }
    }
}
