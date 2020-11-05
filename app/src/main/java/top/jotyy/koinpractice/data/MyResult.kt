package top.jotyy.koinpractice.data

sealed class MyResult<out T>

class Success<out T>(val data: T) : MyResult<T>()
class Failure(val error: String) : MyResult<Nothing>()

sealed class State : MyResult<Nothing>() {
    object Loading : State()
    object Loaded : State()
}
