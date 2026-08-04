package com.example.composepractice

fun onButtonClick(
    currentDisplay: String,
    currentExpression: String,
    pressed: String,
    firstValue: Double?,
    currentOperator: String
): CalculatorResult {

    var display = currentDisplay
    var expression = currentExpression
    var first = firstValue
    var operator = currentOperator

    when (pressed) {

        "C" -> {
            return CalculatorResult(
                display = "0",
                expression = "",
                firstValue = null,
                operator = ""
            )
        }

        "±" -> {

            val value = display.toDoubleOrNull() ?: 0.0
            display = formatNumber(value * -1)

            if (expression.isEmpty()) {
                expression = display
            } else {
                expression = expression.dropLastWhile { it.isDigit() || it == '.' } + display
            }
        }

        "%" -> {

            val value = display.toDoubleOrNull() ?: 0.0
            display = formatNumber(value / 100)

            if (expression.isEmpty()) {
                expression = display
            } else {
                expression = expression.dropLastWhile { it.isDigit() || it == '.' } + display
            }

        }

        "+", "-", "×", "÷" -> {

            if (expression.isEmpty()) {
                expression = display + pressed
            } else {

                val last = expression.last()

                if (last == '+' ||
                    last == '-' ||
                    last == '×' ||
                    last == '÷'
                ) {

                    expression =
                        expression.dropLast(1) + pressed

                } else {

                    expression += pressed

                }

            }

            first = display.toDoubleOrNull()
            operator = pressed
            display = ""

        }

        "." -> {

            if (!display.contains(".")) {

                if (display.isEmpty())
                    display = "0."

                else
                    display += "."

                expression += "."
            }

        }        "=" -> {

        if (first != null && operator.isNotEmpty()) {

            val second = display.toDoubleOrNull() ?: 0.0

            val answer = when (operator) {

                "+" -> first + second

                "-" -> first - second

                "×" -> first * second

                "÷" -> {

                    if (second == 0.0) {

                        return CalculatorResult(
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

            if (display == "0" || display.isEmpty()) {
                display = pressed
            } else {
                display += pressed
            }

            if (expression == display) {
                expression = display
            } else {

                if (expression.isEmpty()) {
                    expression = display
                } else {

                    val last = expression.last()

                    if (last == '+' ||
                        last == '-' ||
                        last == '×' ||
                        last == '÷'
                    ) {

                        expression += display

                    } else {

                        val index = expression.indexOfLast {
                            it == '+' ||
                                    it == '-' ||
                                    it == '×' ||
                                    it == '÷'
                        }

                        expression =
                            if (index == -1)
                                display
                            else
                                expression.substring(0, index + 1) + display
                    }

                }

            }

        }

    }

    return CalculatorResult(
        display = display,
        expression = expression,
        firstValue = first,
        operator = operator
    )

}