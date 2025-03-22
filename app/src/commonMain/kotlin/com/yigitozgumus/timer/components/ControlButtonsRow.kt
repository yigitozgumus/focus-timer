package com.yigitozgumus.timer.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.PauseCircle
import compose.icons.lineawesomeicons.PlayCircle
import compose.icons.lineawesomeicons.StopCircle
import compose.icons.lineawesomeicons.SyncSolid

@Composable
fun ControlButtonsRow(
    modifier: Modifier = Modifier,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onReset: () -> Unit,
    onStop: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ControlButton(
            image = LineAwesomeIcons.PlayCircle,
            label = "Play",
            tint = Color(0xFF4CAF50)
        ) { onPlay() }

        ControlButton(
            image = LineAwesomeIcons.PauseCircle,
            label = "Pause",
            tint = Color(0xFFFFA000)
        ) { onPause() }

        ControlButton(
            image = LineAwesomeIcons.SyncSolid,
            label = "Reset",
            tint = Color(0xFF2196F3)
        ) { onReset() }

        ControlButton(
            image = LineAwesomeIcons.StopCircle,
            label = "Stop",
            tint = Color(0xFFF44336)
        ) { onStop() }
    }
}

@Preview
@Composable
private fun ControlButtonsRowPreview() {
    ControlButtonsRow(onPlay = {}, onPause = {}, onReset = {}, onStop = {})
}
