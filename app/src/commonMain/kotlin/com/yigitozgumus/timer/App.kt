package com.yigitozgumus.timer

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yigitozgumus.timer.components.ControlButtonsRow
import com.yigitozgumus.timer.components.SettingsDialog
import com.yigitozgumus.timer.components.TimerColumn
import com.yigitozgumus.timer.components.TimerConfigurationRow

@Composable
fun TimerApp(
    viewModel: TimerViewModel,
    isSettingsVisible: Boolean,
    toggleSettings: (Boolean) -> Unit
) {
    val settings by viewModel.settings.collectAsState()

    MaterialTheme(colorScheme = lightColorScheme()) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Box(
                modifier =
                    Modifier.fillMaxSize()
                        .background(
                            brush =
                                Brush.verticalGradient(
                                    colors =
                                        listOf(
                                            Color(0xFFF5F5F5),
                                            Color(0xFFE0E0E0)
                                        )
                                )
                        )
            ) { FocusTimerScreen(viewModel) }
        }
    }

    if (isSettingsVisible) {
        SettingsDialog(
            settings = settings,
            onDismiss = { toggleSettings(false) },
            onSettingsChange = { viewModel.updateSettings(it) }
        )
    }
}

@Composable
fun FocusTimerScreen(viewModel: TimerViewModel) {
    val firstTimerState by viewModel.firstTimerState.collectAsState()
    val secondTimerState by viewModel.secondTimerState.collectAsState()
    val hasStarted by viewModel.hasStarted.collectAsState()
    val minuteConfiguration by viewModel.minuteConfig.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TimerConfigurationRow(
            modifier = Modifier,
            minuteConfiguration = minuteConfiguration,
            hasStarted = hasStarted,
            onChange = { isPlayerOne, delta -> viewModel.changeTime(isPlayerOne, delta) }
        )
        // Timer section
        Row(
            modifier = Modifier.weight(0.8f).fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TimerColumn(
                modifier = Modifier.weight(1f),
                state = firstTimerState,
                onTimerClick = { viewModel.handleTimerClick(isPlayerOne = true) }
            )

            TimerColumn(
                modifier = Modifier.weight(1f),
                state = secondTimerState,
                onTimerClick = { viewModel.handleTimerClick(isPlayerOne = false) }
            )
        }

        // Control buttons section
        Card(
            modifier = Modifier.fillMaxWidth().height(100.dp).padding(top = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            ControlButtonsRow(
                modifier = Modifier.padding(12.dp),
                onPlay = { viewModel.play() },
                onPause = { viewModel.pause() },
                onReset = { viewModel.reset() },
                onStop = { viewModel.stop() }
            )
        }
    }
}

@Preview
@Composable
private fun TimerAppPreview() {
    TimerApp(TimerViewModel(), false, {})
}

@Preview
@Composable
private fun FocusTimerScreenPreview() {
    val viewModel = TimerViewModel()
    FocusTimerScreen(viewModel)
}
