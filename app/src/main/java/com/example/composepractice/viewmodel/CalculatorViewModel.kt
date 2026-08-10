package com.example.composepractice.viewmodel

import androidx.lifecycle.ViewModel
import com.example.composepractice.logic.CalculatorEngine
import com.example.composepractice.model.CalculatorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * CalculatorViewModel — the "ViewModel" for the Calculator screen.
 *
 * Simple English — THIS IS THE ROTATION FIX:
 * Before, all calculator state (display, expression, firstValue, operator)
 * lived inside `remember { mutableStateOf(...) }` directly in the Composable.
 * `remember` only survives as long as the Composable itself is alive — and
 * rotating the phone destroys and recreates the Activity (and therefore the
 * Composable), wiping the state back to defaults.
 *
 * A ViewModel is different: Android keeps it alive in a separate store
 * (the ViewModelStore) that survives configuration changes like rotation.
 * It's only cleared when the Activity finishes for good (e.g. the user
 * presses back and leaves the app, not just rotates it).
 *
 * By moving `display`, `expression`, `firstValue`, and `operator` into this
 * ViewModel (bundled together as one CalculatorState), rotating the screen
 * no longer resets the calculator — the current number and operation stay
 * exactly as they were.
 */
class CalculatorViewModel : ViewModel() {

    private val _state = MutableStateFlow(CalculatorState())
    val state: StateFlow<CalculatorState> = _state.asStateFlow()

    /**
     * Called every time a button is tapped. Delegates the actual math to
     * CalculatorEngine (pure logic, no Android code), then stores the
     * resulting new state.
     */
    fun onButtonClick(pressed: String) {
        _state.value = CalculatorEngine.process(_state.value, pressed)
    }
}