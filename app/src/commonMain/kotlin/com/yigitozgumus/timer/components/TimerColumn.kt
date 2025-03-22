package com.yigitozgumus.timer.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yigitozgumus.timer.domain.TimerState

@Composable
fun TimerColumn(
    modifier: Modifier = Modifier,
    state: TimerState,
    onTimerClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TimerButton(
            state = state,
            onClick = onTimerClick,
            modifier = Modifier.fillMaxWidth().weight(0.8f),
        )

    }
}
