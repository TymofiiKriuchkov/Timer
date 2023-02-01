package com.example.timer.ui.main

import androidx.lifecycle.ViewModel
import com.example.timer.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _timerButtonText = MutableStateFlow(R.string.start)
    val timerButtonText: StateFlow<Int> = _timerButtonText

    var timerState = TimerState.TIMER_STOPPED

    fun onStartStopClicked() {
        if (timerState == TimerState.TIMER_STOPPED) {
            timerState = TimerState.TIMER_STARTED
            _timerButtonText.value = R.string.stop
            return
        }
        timerState = TimerState.TIMER_STOPPED
        _timerButtonText.value = R.string.start
    }

}

enum class TimerState {
    TIMER_STARTED, TIMER_STOPPED
}