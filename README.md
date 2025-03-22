# Focus Timer

Cross-platform desktop application built with Kotlin Multiplatform and Jetpack Compose for Desktop. This application helps you maintain focus and productivity by providing a simple and elegant timer interface.

## Features

- Clean and modern Material Design 3 UI
- Cross-platform support (Windows, macOS, Linux)
- Native desktop experience
- Simple and intuitive interface

## Technology Stack

- **Kotlin Multiplatform**: For cross-platform development
- **Jetpack Compose for Desktop**: For modern UI development
- **Material Design 3**: For beautiful and consistent UI components
- **Kotlin Coroutines**: For asynchronous programming
- **Font Awesome Icons**: For rich iconography

## Prerequisites

- JDK 17 or later
- Gradle 8.0 or later
- Kotlin 1.9.0 or later

## Installation

### Pre-built Packages

You can download the latest release from the [GitHub Releases](https://github.com/yigitozgumus/focus-timer/releases) page. The following packages are available:

- Windows: `.msi` installer
- macOS: `.dmg` disk image
- Linux: `.deb` package

### Building from Source

1. Clone the repository:
```bash
git clone https://github.com/yourusername/focus-timer.git
cd focus-timer
```

2. Build the project:
```bash
./gradlew build
```

3. Run the application:
```bash
./gradlew run
```

## Distribution

The application can be packaged for different platforms:

- macOS (.dmg)
- Windows (.msi)
- Linux (.deb)

To create a distribution package:
```bash
./gradlew packageMsi  # For Windows
./gradlew packageDmg  # For macOS
./gradlew packageDeb  # For Linux
```

## Project Structure

```
focus-timer/
├── app/                    # Main application module
│   ├── src/               # Source code
│   └── build.gradle.kts   # Module build configuration
├── gradle/                # Gradle wrapper files
├── build.gradle.kts       # Project build configuration
└── settings.gradle.kts    # Project settings
```

## Development

### Creating a Release

1. Update the version in `app/build.gradle.kts`:
```kotlin
packageVersion = "1.0.0" // Update this version
```

2. Create and push a new tag:
```bash
git tag v1.0.0 # Use the same version as in build.gradle.kts
git push origin v1.0.0
```

The GitHub Actions workflow will automatically:
- Build packages for all platforms
- Create a new release
- Upload the packages to the release

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request. 