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
const val SAVED_SECONDS = "saved_seconds"

class MainViewModel @Inject constructor(private val sharedPref : SharedPreferences) : ViewModel() {

    private val _timerStartStopButtonText = MutableStateFlow(R.string.start)
    val timerStartStopButtonText: StateFlow<Int> = _timerStartStopButtonText

    private val _timerValue = MutableStateFlow(DEFAULT_TIMER_VALUE)
    val timerValue: StateFlow<String> = _timerValue

    private var secondsOfTimer = 0L

    private var timer = Timer()

    var timerState = TimerState.TIMER_STOPPED

    private fun initTimerState(){
        val timerStarted = sharedPref.getBoolean(TIMER_STATE, false)
        if (timerStarted) {
            timerState = TimerState.TIMER_STARTED
            _timerStartStopButtonText.value = R.string.stop
            secondsOfTimer = sharedPref.getLong(SAVED_SECONDS, 0L)
            startTimer()
        }
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
        sharedPref.edit().putBoolean(TIMER_STATE, false)
            .putLong(SAVED_SECONDS, 0L)
            .commit()
        timer.cancel()
        timer.purge()
    }

    fun onResume() {
        //restore state if timer was active
        initTimerState()
    }

    fun onPause() {
        //save seconds and timer state
        if (timerState == TimerState.TIMER_STARTED) {
            sharedPref.edit().putBoolean(TIMER_STATE, true)
                .putLong(SAVED_SECONDS, secondsOfTimer)
                .commit()
        }
        timer.cancel()
        timer.purge()
    }

}

enum class TimerState {
    TIMER_STARTED, TIMER_STOPPED
}