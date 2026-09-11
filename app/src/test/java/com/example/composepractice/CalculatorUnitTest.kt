package com.example.composepractice

import com.example.composepractice.logic.CalculatorEngine
import com.example.composepractice.model.CalculatorState
import com.example.composepractice.model.formatNumber
import com.example.composepractice.viewmodel.CalculatorViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Unit tests for Calculator calculation logic and ViewModel.
 * Runs locally on JVM without needing an Android device or emulator.
 */
class CalculatorUnitTest {

    // Helper to simulate a sequence of button presses on CalculatorEngine
    private fun executeSequence(vararg buttons: String): CalculatorState {
        var state = CalculatorState()
        for (button in buttons) {
            state = CalculatorEngine.process(state, button)
        }
        return state
    }

    // ---------- Basic Arithmetic Tests ----------

    @Test
    fun addition_returnsCorrectResult() {
        // Verify 5 + 3 = 8
        val result = executeSequence("5", "+", "3", "=")
        assertEquals("8", result.display)
        assertEquals("8", result.expression)
        assertNull(result.firstValue)
        assertEquals("", result.operator)

        // Verify multi-digit addition: 12 + 34 = 46
        val multiDigitResult = executeSequence("1", "2", "+", "3", "4", "=")
        assertEquals("46", multiDigitResult.display)
    }

    @Test
    fun subtraction_returnsCorrectResult() {
        // Verify 10 - 4 = 6
        val result = executeSequence("1", "0", "-", "4", "=")
        assertEquals("6", result.display)

        // Verify result resulting in negative value: 3 - 7 = -4
        val negativeResult = executeSequence("3", "-", "7", "=")
        assertEquals("-4", negativeResult.display)
    }

    @Test
    fun multiplication_returnsCorrectResult() {
        // Verify 6 × 7 = 42
        val result = executeSequence("6", "×", "7", "=")
        assertEquals("42", result.display)

        // Verify multiplication by zero: 5 × 0 = 0
        val zeroResult = executeSequence("5", "×", "0", "=")
        assertEquals("0", zeroResult.display)
    }

    @Test
    fun division_returnsCorrectResult() {
        // Verify integer division: 20 ÷ 4 = 5
        val result = executeSequence("2", "0", "÷", "4", "=")
        assertEquals("5", result.display)

        // Verify division with floating point result: 7 ÷ 2 = 3.5
        val decimalResult = executeSequence("7", "÷", "2", "=")
        assertEquals("3.5", decimalResult.display)
    }

    // ---------- Decimals, Negatives & Special Inputs ----------

    @Test
    fun decimalCalculations_returnCorrectResult() {
        // Verify 2.5 + 3.75 = 6.25
        val result = executeSequence("2", ".", "5", "+", "3", ".", "7", "5", "=")
        assertEquals("6.25", result.display)

        // Verify that multiple decimal points in one number are ignored
        val singleDecimalResult = executeSequence("2", ".", "5", ".", "5")
        assertEquals("2.55", singleDecimalResult.display)
    }

    @Test
    fun negativeNumbers_negateCorrectlyAndCompute() {
        // Verify plus/minus toggle: 5 -> ± -> -5
        val negated = executeSequence("5", "±")
        assertEquals("-5", negated.display)

        // Verify toggling twice returns to positive: 5 -> ± -> ± -> 5
        val doubleNegated = executeSequence("5", "±", "±")
        assertEquals("5", doubleNegated.display)

        // Verify calculation with negative number: -5 + 8 = 3
        val calcWithNegative = executeSequence("5", "±", "+", "8", "=")
        assertEquals("3", calcWithNegative.display)
    }

    @Test
    fun zeroCalculations_returnCorrectResult() {
        // Initial 0 is replaced when a new digit is pressed
        val initialZero = executeSequence("7")
        assertEquals("7", initialZero.display)

        // 0 + 5 = 5
        val zeroPlusFive = executeSequence("0", "+", "5", "=")
        assertEquals("5", zeroPlusFive.display)
    }

    @Test
    fun divisionByZero_isHandledCorrectly() {
        // Verify dividing by zero produces "Error" and does not crash
        val result = executeSequence("9", "÷", "0", "=")
        assertEquals("Error", result.display)
        assertEquals("", result.expression)
        assertNull(result.firstValue)
    }

    @Test
    fun percentage_calculatesCorrectly() {
        // Verify 50 % = 0.5
        val halfPercent = executeSequence("5", "0", "%")
        assertEquals("0.5", halfPercent.display)

        // Verify 200 % = 2
        val doublePercent = executeSequence("2", "0", "0", "%")
        assertEquals("2", doublePercent.display)
    }

    @Test
    fun clear_resetsStateToDefault() {
        // Verify pressing C resets all state fields to their defaults
        val cleared = executeSequence("1", "2", "3", "+", "4", "5", "C")
        assertEquals("0", cleared.display)
        assertEquals("", cleared.expression)
        assertNull(cleared.firstValue)
        assertEquals("", cleared.operator)
    }

    @Test
    fun operatorReplacement_updatesPendingOperator() {
        // If an operator is selected and another is pressed, it replaces the previous operator
        val state = executeSequence("8", "+", "×", "2", "=")
        assertEquals("16", state.display) // 8 × 2 = 16
    }

    @Test
    fun formatNumber_formatsIntegersAndDecimalsCorrectly() {
        // Whole numbers should not have trailing decimals
        assertEquals("5", formatNumber(5.0))
        assertEquals("0", formatNumber(0.0))
        assertEquals("-10", formatNumber(-10.0))

        // Non-whole numbers should preserve decimal precision
        assertEquals("5.5", formatNumber(5.5))
        assertEquals("3.14159", formatNumber(3.14159))
        assertEquals("-0.25", formatNumber(-0.25))
    }

    // ---------- ViewModel State Management Test ----------

    @Test
    fun calculatorViewModel_updatesStateCorrectlyOnButtonClick() {
        val viewModel = CalculatorViewModel()
        assertEquals("0", viewModel.state.value.display)

        viewModel.onButtonClick("9")
        assertEquals("9", viewModel.state.value.display)

        viewModel.onButtonClick("+")
        viewModel.onButtonClick("1")
        viewModel.onButtonClick("=")
        assertEquals("10", viewModel.state.value.display)
    }
}
