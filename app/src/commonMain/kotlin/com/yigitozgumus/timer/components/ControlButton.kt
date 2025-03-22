package com.yigitozgumus.timer.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.PauseCircle
import compose.icons.lineawesomeicons.PlayCircle
import compose.icons.lineawesomeicons.StopCircle

@Composable
fun ControlButton(
    modifier: Modifier = Modifier,
    image: ImageVector,
    label: String,
    tint: Color = Color(0xFF2C3E50),
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.width(IntrinsicSize.Min),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onClick, modifier = Modifier.size(40.dp)) {
            Icon(
                imageVector = image,
                contentDescription = label,
                tint = tint,
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            text = label,
            fontSize = 12.sp,
            color = tint,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun ControlButtonPreview() {
    ControlButton(
        image = LineAwesomeIcons.PlayCircle,
        label = "Play",
        tint = Color(0xFF4CAF50),
        onClick = {}
    )
}

@Preview
@Composable
private fun ControlButtonsPreview() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ControlButton(
            image = LineAwesomeIcons.PlayCircle,
            label = "Play",
            tint = Color(0xFF4CAF50),
            onClick = {}
        )
        ControlButton(
            image = LineAwesomeIcons.PauseCircle,
            label = "Pause",
            tint = Color(0xFFFFA000),
            onClick = {}
        )
        ControlButton(
            image = LineAwesomeIcons.StopCircle,
            label = "Stop",
            tint = Color(0xFFF44336),
            onClick = {}
        )
    }
}
