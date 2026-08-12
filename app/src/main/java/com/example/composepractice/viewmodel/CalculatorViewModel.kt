package com.example.composepractice.viewmodel

import androidx.lifecycle.ViewModel
import com.example.composepractice.logic.CalculatorEngine
import com.example.composepractice.model.CalculatorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class CalculatorViewModel : ViewModel() {

    private val _state = MutableStateFlow(CalculatorState())
    val state: StateFlow<CalculatorState> = _state.asStateFlow()


    fun onButtonClick(pressed: String) {
        _state.value = CalculatorEngine.process(_state.value, pressed)
    }
}