# Movie Compose App 🎬

A modern Android application for browsing popular movies, built with [Jetpack Compose](https://developer.android.com/jetpack/compose) and [The Movie Database (TMDB) API](https://www.themoviedb.org/documentation/api).

This project demonstrates professional Android development practices, including Clean Architecture, MVVM, Dependency Injection, and reactive programming.

## 📱 Features

*   **Modern UI**: Native Android UI built 100% with Jetpack Compose.
*   **Popular Movies**: Browse a list of currently popular movies.
*   **Movie Details**: View detailed information about specific movies, including rating, release date, and overview.
*   **Network Inspection**: Integrated Chucker for monitoring HTTP requests/responses in debug builds.
*   **Architecture**: Scalable and testable structure using Clean Architecture principles.

## 🛠 Tech Stack

*   **Language**: [Kotlin](https://kotlinlang.org/)
*   **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) - Material 3
*   **Architecture Pattern**: MVVM (Model-View-ViewModel) with Clean Architecture (Presentation, Domain, Data layers)
*   **Dependency Injection**: [Hilt](https://dagger.dev/hilt/)
*   **Networking**: [Retrofit](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/)
*   **Image Loading**: [Coil](https://coil-kt.github.io/coil/)
*   **Async/Concurrency**: [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
*   **Testing**: JUnit, Mockk, Turbine

## 🏗 Architecture

The app follows **Clean Architecture** guidelines to separate concerns and ensure testability:

1.  **Presentation Layer**: UI (Composables) and State Holders (ViewModels).
2.  **Domain Layer**: Business logic (Use Cases) and repository interfaces. Plain Kotlin modules with no Android dependencies.
3.  **Data Layer**: Repository implementations, API services (Retrofit), and data models (DTOs).

## 🚀 Setup & Installation

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/yourusername/movie-compose-app.git
    ```

2.  **Open in Android Studio**:
    Open the project in the latest version of Android Studio (Koala or newer recommended).

3.  **API Key Configuration**:
    The project requires a TMDB API Key.
    *   Get your API key from [TMDB](https://www.themoviedb.org/settings/api).
    *   The app currently has a demo key configured in `build.gradle.kts`.
    *   *Best Practice*: For your own fork, add `TMDB_API_KEY="your_api_key_here"` to your `local.properties` file and update the `build.gradle.kts` to read from it.

4.  **Build and Run**:
    Select the `app` configuration and run on an Emulator or Physical Device.

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
