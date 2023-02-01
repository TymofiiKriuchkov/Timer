package com.example.timer.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.timer.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import javax.inject.Inject


const val DEFAULT_TIMER_VALUE = "00:00:00"
const val TIMER_FORMAT = "%02d:%02d:%02d"
const val TIMER_STATE = "timer_state"

class MainViewModel @Inject constructor(val sharedPref : SharedPreferences) : ViewModel() {

    private val _timerStartStopButtonText = MutableStateFlow(R.string.start)
    val timerStartStopButtonText: StateFlow<Int> = _timerStartStopButtonText

    private val _timerValue = MutableStateFlow(DEFAULT_TIMER_VALUE)
    val timerValue: StateFlow<String> = _timerValue

    private var secondsOfTimer = 0L

    private var timer = Timer()

    var timerState = TimerState.TIMER_STOPPED

    init {
        //todo restore timer state and seconds quantity

        val timerStarted = sharedPref.getBoolean(TIMER_STATE, false)

    }

    fun onStartStopClicked() {
        if (timerState == TimerState.TIMER_STOPPED) {
            timerState = TimerState.TIMER_STARTED
            _timerStartStopButtonText.value = R.string.stop
            startTimer()
            return
        }
        timerState = TimerState.TIMER_STOPPED
        _timerStartStopButtonText.value = R.string.start
        _timerValue.value = DEFAULT_TIMER_VALUE
        secondsOfTimer = 0
        stopTimer()
    }

    private fun startTimer() {
        //todo get saved seconds

        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val hours: Int = (secondsOfTimer / 3600).toInt()
                val minutes: Int = (secondsOfTimer % 3600 / 60).toInt()
                val seconds: Int = (secondsOfTimer % 60).toInt()

                val time = String.format(Locale.getDefault(), TIMER_FORMAT, hours, minutes, seconds)
                _timerValue.value = time

                ++secondsOfTimer
            }

        }, 0, 1000)
    }

    private fun stopTimer() {
        timer.cancel()
        timer.purge()
    }

    fun onResume() {
        //todo restore state if timer was active

        //
        if (timerState == TimerState.TIMER_STARTED) {
            startTimer()
        }
    }

    fun onPause() {
        //todo save seconds and timer state

        stopTimer()
    }

}

enum class TimerState {
    TIMER_STARTED, TIMER_STOPPED
}