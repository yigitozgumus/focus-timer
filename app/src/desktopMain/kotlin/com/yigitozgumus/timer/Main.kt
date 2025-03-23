package com.yigitozgumus.timer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    val viewModel = TimerViewModel()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Focus Timer",
    ) {
        var showSettings by remember { mutableStateOf(false) }
        TimerApp(viewModel, showSettings) { showSettings = it }
        MenuBar { Menu("File") { Item("Settings", onClick = { showSettings = true }) } }
    }
}
