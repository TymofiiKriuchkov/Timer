package com.example.timer

import com.example.timer.ui.main.MainViewModel
import com.example.timer.ui.main.TimerState
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @Mock
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel()
    }

    @Test
    fun on_start_clicked() {
        viewModel.timerState = TimerState.TIMER_STOPPED
        viewModel.onStartStopClicked()
        Assert.assertEquals(viewModel.timerState, TimerState.TIMER_STARTED)
    }

    @Test
    fun on_stop_clicked() {
        viewModel.timerState = TimerState.TIMER_STARTED
        viewModel.onStartStopClicked()
        Assert.assertEquals(viewModel.timerState, TimerState.TIMER_STOPPED)
    }

    @Test
    fun on_stop_text_changed() {
        viewModel.timerState = TimerState.TIMER_STARTED
        viewModel.onStartStopClicked()
        Assert.assertEquals(viewModel.timerStartStopButtonText.value, R.string.start)
    }

    @Test
    fun on_start_text_changed() {
        viewModel.timerState = TimerState.TIMER_STOPPED
        viewModel.onStartStopClicked()
        Assert.assertEquals(viewModel.timerStartStopButtonText.value, R.string.stop)
    }


}