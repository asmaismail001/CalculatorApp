package com.example.composepractice.logic

import com.example.composepractice.model.CalculatorState
import com.example.composepractice.model.formatNumber

/**
 * CalculatorEngine — pure calculation logic, no Android/UI code at all.
 *
 * Simple English:
 * This is your original `onButtonClick()` function, moved here and renamed
 * to `process()`. The math logic is 100% UNCHANGED — every button behaves
 * exactly like before. The only difference is *where* it lives:
 * - Before: it was a loose function floating in the same file as the UI.
 * - Now: it's grouped in its own file, so it's clear this is "business logic",
 *   separate from both the ViewModel (which manages state) and the View
 *   (which draws the screen).
 *
 * Being a "pure function" means: same input always gives same output,
 * and it doesn't touch anything outside itself (no side effects). This
 * makes it very easy to unit test without needing a screen or a ViewModel.
 */
object CalculatorEngine {

    fun process(currentState: CalculatorState, pressed: String): CalculatorState {

        var display = currentState.display
        var expression = currentState.expression
        var first = currentState.firstValue
        var operator = currentState.operator

        when (pressed) {

            "C" -> {
                return CalculatorState(
                    display = "0",
                    expression = "",
                    firstValue = null,
                    operator = ""
                )
            }

            "±" -> {
                val value = display.toDoubleOrNull() ?: 0.0
                display = formatNumber(value * -1)

                expression = if (expression.isEmpty()) {
                    display
                } else {
                    expression.dropLastWhile { it.isDigit() || it == '.' } + display
                }
            }

            "%" -> {
                val value = display.toDoubleOrNull() ?: 0.0
                display = formatNumber(value / 100)

                expression = if (expression.isEmpty()) {
                    display
                } else {
                    expression.dropLastWhile { it.isDigit() || it == '.' } + display
                }
            }

            "+", "-", "×", "÷" -> {

                if (expression.isEmpty()) {
                    expression = display + pressed
                } else {
                    val last = expression.last()

                    expression = if (last == '+' || last == '-' || last == '×' || last == '÷') {
                        expression.dropLast(1) + pressed
                    } else {
                        expression + pressed
                    }
                }

                first = display.toDoubleOrNull()
                operator = pressed
                display = ""
            }

            "." -> {
                if (!display.contains(".")) {
                    display = if (display.isEmpty()) "0." else display + "."
                    expression += "."
                }
            }

            "=" -> {
                if (first != null && operator.isNotEmpty()) {

                    val second = display.toDoubleOrNull() ?: 0.0

                    val answer = when (operator) {
                        "+" -> first + second
                        "-" -> first - second
                        "×" -> first * second
                        "÷" -> {
                            if (second == 0.0) {
                                return CalculatorState(
                                    display = "Error",
                                    expression = "",
                                    firstValue = null,
                                    operator = ""
                                )
                            }
                            first / second
                        }
                        else -> second
                    }

                    display = formatNumber(answer)
                    expression = display
                    first = null
                    operator = ""
                }
            }

            else -> {
                display = if (display == "0" || display.isEmpty()) pressed else display + pressed

                expression = if (expression.isEmpty()) {
                    display
                } else {
                    val last = expression.last()

                    if (last == '+' || last == '-' || last == '×' || last == '÷') {
                        expression + display
                    } else {
                        val index = expression.indexOfLast {
                            it == '+' || it == '-' || it == '×' || it == '÷'
                        }
                        if (index == -1) display else expression.substring(0, index + 1) + display
                    }
                }
            }
        }

        return CalculatorState(
            display = display,
            expression = expression,
            firstValue = first,
            operator = operator
        )
    }
}