package com.yigitozgumus.timer.domain

sealed class TimerState(open val label: String) {

    data class Started(
        override val label: String,
        val isActive: Boolean,
        val isTimerActive: Boolean,
        val progress: Float
    ) : TimerState(label)

    data class NotStarted(override val label: String, val isTimerActive: Boolean) : TimerState(label)
}