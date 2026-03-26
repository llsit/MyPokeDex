# MyPokeDeX

A modern PokeDeX application built with **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**. It demonstrates a clean architecture approach, modern UI components, and state-of-the-art libraries for a consistent experience across Android, iOS, and Desktop.

## 🚀 Features

- **Multiplatform Support**: Shared business logic and UI across Android, iOS, and Desktop (JVM).
- **Modern UI**: Built with Jetpack Compose / Compose Multiplatform using Material 3.
- **PokeAPI Integration**: Fetches real-time data from the popular [PokeAPI](https://pokeapi.co/).
- **Efficient Image Loading**: Uses Coil 3 with Ktor integration for cross-platform image fetching.
- **Reactive Navigation**: Implements `Navigation3` for type-safe, multiplatform-ready navigation.
- **Dependency Injection**: Uses Koin for flexible and lightweight DI.
- **Robust Networking**: Uses Ktor 3 with platform-specific engines (OkHttp for Android/JVM, Darwin for iOS).

## 🛠️ Tech Stack & Libraries

### Core
- **Kotlin Multiplatform**: Shared code between mobile and desktop.
- **Compose Multiplatform**: Declarative UI for all platforms.
- **Coroutines & Flow**: Asynchronous programming and reactive data streams.
- **Kotlinx Serialization**: JSON parsing and data modeling.

### Networking & Data
- **Ktor (3.0.1)**: HTTP client for multiplatform networking.
- **Coil (3.4.0)**: Image loading library with KMP support.
- **Room (2.8.4)**: Local persistence (configured for KMP).

### Architecture & Navigation
- **Koin (4.0.0)**: Dependency injection.
- **Navigation3**: Modern, type-safe navigation for KMP.
- **Lifecycle ViewModel**: Shared ViewModels with platform-aware lifecycle management.

## 📂 Project Structure

- `composeApp/src/commonMain`: Shared UI and business logic (Repository, UseCases, ViewModels, Composables).
- `composeApp/src/androidMain`: Android-specific implementations and entry point.
- `composeApp/src/iosMain`: iOS-specific implementations.
- `composeApp/src/jvmMain`: Desktop-specific implementations.
- `iosApp`: Swift project for the iOS target.

## ⚙️ Requirements

- **Android Studio Ladybug** or newer.
- **Xcode** (for iOS development).
- **JDK 11** or newer (Project uses JVM 11 target).

## 🏃 Getting Started

### Android
Open the project in Android Studio and run the `composeApp` configuration.

### iOS
1. Open the `iosApp/iosApp.xcodeproj` in Xcode.
2. Build and run the project.

### Desktop
Run the following command in the terminal:
```bash
./gradlew :composeApp:run
```

---

Built with ❤️ using Kotlin Multiplatform.
