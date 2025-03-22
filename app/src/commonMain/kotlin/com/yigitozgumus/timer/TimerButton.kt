package com.yigitozgumus.timer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimerButton(
    modifier: Modifier = Modifier,
    time: Int,
    isActive: Boolean,
    progress: Float = 1f,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .background(
                color = if (isActive) progressToColor(progress) else Color.Gray,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
            text = String.format("%d:%02d:%02d", time / 3600, time / 60 - ((time / 3600) * 60), time % 60),
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

fun progressToColor(progress: Float): Color {
    val clamped = progress.coerceIn(0f, 1f)

    // Interpolate hue from 0 (red) to 120 (green)
    val hue = clamped * 120f
    val saturation = 1f
    val lightness = 0.5f

    // Convert HSL to RGB manually
    val c = (1f - kotlin.math.abs(2 * lightness - 1f)) * saturation
    val x = c * (1f - kotlin.math.abs((hue / 60f) % 2 - 1f))
    val m = lightness - c / 2f

    val (r1, g1, b1) = when {
        hue < 60f -> Triple(c, x, 0f)
        hue < 120f -> Triple(x, c, 0f)
        else -> Triple(0f, 1f, 0f) // Clamp top-end to green
    }

    val r = ((r1 + m) * 255).toInt().coerceIn(0, 255)
    val g = ((g1 + m) * 255).toInt().coerceIn(0, 255)
    val b = ((b1 + m) * 255).toInt().coerceIn(0, 255)

    return Color(red = r, green = g, blue = b).copy(alpha = 0.6f)
}