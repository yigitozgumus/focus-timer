package com.yigitozgumus.timer

import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val defaultTime = 1800

class TimerViewModel : ViewModel() {
    private val _playerOneTime = MutableStateFlow(defaultTime)
    val playerOneTime: StateFlow<Int> = _playerOneTime

    private val _playerTwoTime = MutableStateFlow(defaultTime)
    val playerTwoTime: StateFlow<Int> = _playerTwoTime

    private val _playerOneResetTime = MutableStateFlow(defaultTime)
    val playerOneResetTime: StateFlow<Int> = _playerOneResetTime

    private val _playerTwoResetTime = MutableStateFlow(defaultTime)
    val playerTwoResetTime: StateFlow<Int> = _playerTwoResetTime

    private val _isPlayerOneTurn = MutableStateFlow(true)
    val isPlayerOneTurn: StateFlow<Boolean> = _isPlayerOneTurn

    private val _isRunning = MutableStateFlow(false)

    private val _hasStarted = MutableStateFlow(false)
    val hasStarted: StateFlow<Boolean> = _hasStarted

    private val _wasPaused = MutableStateFlow(false)

    val areControlButtonsEnabled: StateFlow<Boolean> = MutableStateFlow(!_hasStarted.value)

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
    }

    fun stop() {
        _isRunning.value = false
        _hasStarted.value = false
        _wasPaused.value = false
    }

    fun handleTimerClick(isPlayerOne: Boolean) {
        if (!_hasStarted.value) {
            _isPlayerOneTurn.value = isPlayerOne
            _isRunning.value = true
            _hasStarted.value = true
        } else {
            _isPlayerOneTurn.value = !isPlayerOne
        }
    }

    fun changeTime(isPlayerOne: Boolean, delta: Int) {
        if (isPlayerOne) {
            val newTime = (_playerOneTime.value + delta).coerceAtLeast(0)
            _playerOneTime.value = newTime
            _playerOneResetTime.value = newTime
        } else {
            val newTime = (_playerTwoTime.value + delta).coerceAtLeast(0)
            _playerTwoTime.value = newTime
            _playerTwoResetTime.value = newTime
        }
    }
}