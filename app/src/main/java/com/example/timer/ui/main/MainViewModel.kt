package com.example.timer.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _timerButtonState = MutableStateFlow(TimerState.TIMER_STOPPED)
    val timerButtonState: StateFlow<TimerState> = _timerButtonState

    fun onStartStopClicked() {
        if (_timerButtonState.value == TimerState.TIMER_STOPPED) {
            _timerButtonState.value = TimerState.TIMER_STARTED
            return
        }
        _timerButtonState.value = TimerState.TIMER_STOPPED
    }

}

enum class TimerState {
    TIMER_STARTED, TIMER_STOPPED
}