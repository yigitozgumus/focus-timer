package com.yigitozgumus.timer.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yigitozgumus.timer.domain.TimerState

@Composable
fun TimerButton(modifier: Modifier = Modifier, state: TimerState, onClick: () -> Unit) {
    val modifier =
        when (state) {
            is TimerState.NotStarted -> {
                modifier.clip(RoundedCornerShape(24.dp)).clickable(
                    enabled = state.isTimerActive,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onClick() }
            }

            is TimerState.Started -> {
                val animatedProgress by
                animateFloatAsState(
                    targetValue = state.progress,
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                )

                val pulseAnimation = rememberInfiniteTransition()
                val scale by
                pulseAnimation.animateFloat(
                    initialValue = 1f,
                    targetValue = if (state.isActive) 1.02f else 1f,
                    animationSpec =
                        infiniteRepeatable(
                            animation =
                                tween(
                                    1000,
                                    easing = FastOutSlowInEasing
                                ),
                            repeatMode = RepeatMode.Reverse
                        )
                )
                modifier.scale(scale)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        brush =
                            Brush.verticalGradient(
                                colors =
                                    if (state.isActive) {
                                        listOf(
                                            progressToColor(
                                                animatedProgress
                                            ),
                                            progressToColor(
                                                animatedProgress
                                            )
                                                .copy(alpha = 0.8f)
                                        )
                                    } else {
                                        listOf(
                                            Color.White,
                                            Color(0xFFF5F5F5)
                                        )
                                    }
                            )
                    )
                    .clickable(
                        enabled = state.isTimerActive,
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onClick() }
            }
        }
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(
            text = state.label,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = if (false) Color.White else Color(0xFF2C3E50),
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

    val (r1, g1, b1) =
        when {
            hue < 60f -> Triple(c, x, 0f)
            hue < 120f -> Triple(x, c, 0f)
            else -> Triple(0f, 1f, 0f) // Clamp top-end to green
        }

    val r = ((r1 + m) * 255).toInt().coerceIn(0, 255)
    val g = ((g1 + m) * 255).toInt().coerceIn(0, 255)
    val b = ((b1 + m) * 255).toInt().coerceIn(0, 255)

    return Color(red = r, green = g, blue = b).copy(alpha = 0.4f)
}
