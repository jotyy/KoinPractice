package top.jotyy.koinpractice.data

sealed class HttpResult<out T>

class Success<out T>(val data: T) : HttpResult<T>()
class Failure(val error: String) : HttpResult<Nothing>()

sealed class State : HttpResult<Nothing>() {
    object Loading : State()
    object Loaded : State()
}
