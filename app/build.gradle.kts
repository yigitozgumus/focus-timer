import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvm("desktop")
    sourceSets {
        val commonMain by getting {
            dependencies {

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(compose.preview)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.composeIcons.fontAwesome)
                implementation(libs.lifecycle.viewmodel.compose)
            }
        }
        val desktopMain by getting {
            dependencies {
                    implementation(compose.desktop.currentOs)
                    implementation(libs.kotlinx.coroutines.swing)
            }
        }
    }
}
compose.desktop {
    application {
        mainClass = "com.yigitozgumus.timer.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Focus Timer"
            packageVersion = libs.versions.focus.timer.get()

            macOS {
                bundleID = "com.yigitozgumus.timer"
                iconFile.set(project.file("src/desktopMain/resources/FocusTimer.icns"))
            }
        }
    }
}
