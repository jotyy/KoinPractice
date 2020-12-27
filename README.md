# Android MVVM Sample & Hilt/Koin

### 📢 This is an Android MVVM Architecture Sample.

Here is the architecture graph of this project.

<img src="https://github.com/jotyy/KoinPractice/blob/master/images/MVVM-Architecture.png" alt="image-20201105182731125" style="zoom:67%;" />

### 🔨 Framworks/Libraries

In this project, I worked with these frameworks.

- Minimum SDK level 21
- 100% [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- JetPack
  - LiveData - notify domain layer data to views.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct database.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository pattern
  - [Koin](https://github.com/InsertKoinIO/koin) - dependency injection
- [Ktor & Gson](https://github.com/ktorio/ktor) - constructing the REST API
- [Picasso](https://github.com/square/picasso) - loading images

### 📜 Article

[【Android架构】依赖注入篇Dagger&Koin](https://www.jianshu.com/p/2fe98b69031b)
