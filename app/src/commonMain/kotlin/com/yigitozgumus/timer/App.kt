package com.yigitozgumus.timer

import ControlButton
import ControlPanel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.PauseCircle
import compose.icons.lineawesomeicons.PlayCircle
import compose.icons.lineawesomeicons.StopCircle
import compose.icons.lineawesomeicons.SyncSolid
import kotlinx.coroutines.delay
import kotlin.math.max

@Composable
fun TimerApp() {
    val viewModel = TimerViewModel()
    FocusTimerScreen(viewModel)
}

@Composable
fun FocusTimerScreen(viewModel: TimerViewModel) {
    val playerOneTime by viewModel.playerOneTime.collectAsState()
    val playerTwoTime by viewModel.playerTwoTime.collectAsState()
    val playerOneResetTime by viewModel.playerOneResetTime.collectAsState()
    val playerTwoResetTime by viewModel.playerTwoResetTime.collectAsState()
    val isPlayerOneTurn by viewModel.isPlayerOneTurn.collectAsState()
    val hasStarted by viewModel.hasStarted.collectAsState()
    val areControlButtonsEnabled by viewModel.areControlButtonsEnabled.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ControlButtonsRow(
            modifier = Modifier,
            onPlay = { viewModel.play() },
            onPause = { viewModel.pause() },
            onReset = { viewModel.reset() },
            onStop = { viewModel.stop() }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "Focused Work",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            )
            Text(
                text = "Shallow Work",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            )
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TimerColumn(
                modifier = Modifier.weight(1f),
                time = playerOneTime,
                areControlButtonsEnabled = areControlButtonsEnabled,
                isActive = hasStarted && isPlayerOneTurn,
                onTimerClick = { viewModel.handleTimerClick(isPlayerOne = true) },
                onChangeTime = { delta -> viewModel.changeTime(isPlayerOne = true, delta = delta) },
                progress = playerOneTime / playerOneResetTime.toFloat()
            )

            TimerColumn(
                modifier = Modifier.fillMaxHeight().weight(1f),
                time = playerTwoTime,
                isActive = hasStarted && !isPlayerOneTurn,
                onTimerClick = { viewModel.handleTimerClick(isPlayerOne = false) },
                onChangeTime = { delta -> viewModel.changeTime(isPlayerOne = false, delta = delta) },
                progress = playerTwoTime / playerTwoResetTime.toFloat()
            )
        }
    }
}

@Composable
fun ControlButtonsRow(
    modifier: Modifier = Modifier,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onReset: () -> Unit,
    onStop: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ControlButton(image = LineAwesomeIcons.PlayCircle, shape = CircleShape, label = "Play") {
            onPlay()
        }
        ControlButton(image = LineAwesomeIcons.PauseCircle, shape = CircleShape, label = "Pause") {
            onPause()
        }
        ControlButton(image = LineAwesomeIcons.SyncSolid, shape = CircleShape, label = "Reset") {
            onReset()
        }
        ControlButton(image = LineAwesomeIcons.StopCircle, shape = CircleShape, label = "Stop") {
            onStop()
        }
    }
}

@Composable
fun TimerColumn(
    modifier: Modifier = Modifier,
    time: Int,
    areControlButtonsEnabled: Boolean = true,
    isActive: Boolean,
    progress: Float,
    onTimerClick: () -> Unit,
    onChangeTime: (Int) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        TimerButton(
            time = time,
            isActive = isActive,
            onClick = onTimerClick,
            modifier = Modifier.weight(1f),
            progress = progress
        )
        Spacer(modifier = Modifier.height(8.dp))
        ControlPanel(
            modifier = Modifier,
            onValueChange = onChangeTime,
            areControlButtonsEnabled = areControlButtonsEnabled
        )
    }
}
