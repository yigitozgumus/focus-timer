package com.yigitozgumus.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yigitozgumus.timer.domain.DesktopSoundPlayer
import com.yigitozgumus.timer.domain.MinuteConfiguration
import com.yigitozgumus.timer.domain.Settings
import com.yigitozgumus.timer.domain.TimerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private const val defaultTime = 1800

class TimerViewModel : ViewModel() {
    private val soundPlayer = DesktopSoundPlayer()

    private val _settings = MutableStateFlow(Settings())
    val settings: StateFlow<Settings> = _settings.asStateFlow()

    private val _playerOneTime = MutableStateFlow(defaultTime)
    private val _playerTwoTime = MutableStateFlow(defaultTime)
    private val _playerOneResetTime = MutableStateFlow(defaultTime)
    private val _playerTwoResetTime = MutableStateFlow(defaultTime)
    private val _isPlayerOneTurn = MutableStateFlow(true)
    private val _isRunning = MutableStateFlow(false)
    private val _hasStarted = MutableStateFlow(false)
    private val _wasPaused = MutableStateFlow(false)
    private val _minuteConfig = MutableStateFlow(MinuteConfiguration())
    val minuteConfig: StateFlow<MinuteConfiguration> = _minuteConfig.asStateFlow()

    val hasStarted: StateFlow<Boolean> = _hasStarted.asStateFlow()

    val isTimerActive: StateFlow<Boolean> =
        _hasStarted
            .combine(_wasPaused) { hasStarted, wasPaused ->
                if (hasStarted.not()) true else wasPaused.not()
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, true)

    val firstTimerState =
        combine(_playerOneTime, _playerOneResetTime, _hasStarted, _isPlayerOneTurn) { playerOneTime,
                                                                                      playerOneResetTime,
                                                                                      hasStarted,
                                                                                      isPlayerOneTurn ->
            TimerState.Started(
                label = getTimeLabel(playerOneTime),
                isActive = hasStarted && isPlayerOneTurn,
                isTimerActive = isTimerActive.value,
                progress = playerOneTime / playerOneResetTime.toFloat()
            )
        }
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                TimerState.NotStarted(
                    getTimeLabel(_playerOneTime.value),
                    isTimerActive.value
                )
            )

    val secondTimerState =
        combine(_playerTwoTime, _playerTwoResetTime, _hasStarted, _isPlayerOneTurn) { playerTwoTime,
                                                                                      playerTwoResetTime,
                                                                                      hasStarted,
                                                                                      isPlayerOneTurn ->
            TimerState.Started(
                label = getTimeLabel(playerTwoTime),
                isActive = hasStarted && isPlayerOneTurn.not(),
                isTimerActive = isTimerActive.value,
                progress = playerTwoTime / playerTwoResetTime.toFloat()
            )
        }
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                TimerState.NotStarted(
                    getTimeLabel(_playerTwoTime.value),
                    isTimerActive.value
                )
            )

    private fun getTimeLabel(time: Int): String {
        return String.format("%d:%02d:%02d", time / 3600, (time % 3600) / 60, time % 60)
    }

    init {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                if (_isRunning.value) {
                    if (_isPlayerOneTurn.value) {
                        _playerOneTime.value = (_playerOneTime.value - 1).coerceAtLeast(0)
                    } else {
                        _playerTwoTime.value = (_playerTwoTime.value - 1).coerceAtLeast(0)
                    }
                }
            }
        }
    }

    fun play() {
        if (_hasStarted.value && _wasPaused.value) {
            _isRunning.value = true
            _wasPaused.value = false
        }
    }

    fun pause() {
        _isRunning.value = false
        _wasPaused.value = true
    }

    fun reset() {
        _playerOneTime.value = _playerOneResetTime.value
        _playerTwoTime.value = _playerTwoResetTime.value
        _isRunning.value = false
        _hasStarted.value = false
        _wasPaused.value = false
        _minuteConfig.update { MinuteConfiguration() }
    }

    fun stop() {
        _isRunning.value = false
        _hasStarted.value = false
        _wasPaused.value = false
    }

    fun handleTimerClick(isPlayerOne: Boolean) {
        if (isTimerActive.value.not()) return
        if (!_hasStarted.value) {
            _isPlayerOneTurn.value = isPlayerOne
            _isRunning.value = true
            _hasStarted.value = true
        } else {
            _isPlayerOneTurn.value = !isPlayerOne
        }
        if (_settings.value.isSoundEnabled) {
            soundPlayer.playClickSound()
        }
    }

    fun changeTime(isPlayerOne: Boolean, delta: String) {
        if (delta.isEmpty() || delta.toIntOrNull() == null || delta.toIntOrNull() == 0) return
        val currentDelta = delta.toIntOrNull() ?: return
        if (isPlayerOne) {
            _minuteConfig.update { it.copy(focused = delta) }
        } else {
            _minuteConfig.update { it.copy(shallow = delta) }
        }
        val change =
            if (isPlayerOne) {
                currentDelta * 60 - _playerOneTime.value
            } else {
                currentDelta * 60 - _playerTwoTime.value
            }
        if (isPlayerOne) {
            val newTime = (_playerOneTime.value + change).coerceAtLeast(0)
            _playerOneTime.value = newTime
            _playerOneResetTime.value = newTime
        } else {
            val newTime = (_playerTwoTime.value + change).coerceAtLeast(0)
            _playerTwoTime.value = newTime
            _playerTwoResetTime.value = newTime
        }
    }

    fun updateSettings(settings: Settings) {
        _settings.value = settings
    }
}
