package com.example.composepractice.model

/**
 * CalculatorState — the "Model" for this app.
 *
 * Simple English:
 * Just a plain data box holding everything the calculator currently knows:
 * - display: what's shown as the "current number" (e.g. "42")
 * - expression: the full running expression (e.g. "12+30")
 * - firstValue: the first number in a pending calculation (e.g. 12.0, before you hit "=")
 * - operator: the pending operator ("+", "-", "×", "÷"), or "" if none
 *
 * This replaces the old `CalculatorResult` class — same fields, renamed to
 * "State" because it now represents the calculator's ongoing state, not just
 * a one-off function result.
 *
 * Defaults are set so a "fresh calculator" (display = "0", nothing else)
 * can be created easily: CalculatorState().
 */
data class CalculatorState(
    val display: String = "0",
    val expression: String = "",
    val firstValue: Double? = null,
    val operator: String = ""
)

/**
 * Formats a Double for display: whole numbers show without a decimal point
 * (e.g. 5.0 -> "5"), non-whole numbers show normally (e.g. 5.5 -> "5.5").
 * Unchanged from the original project's formatNumber() function.
 */
fun formatNumber(number: Double): String {
    return if (number % 1 == 0.0) {
        number.toInt().toString()
    } else {
        number.toString()
    }
}