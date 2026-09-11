package com.example.composepractice

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.composepractice.ui.screens.CalculatorScreen
import com.example.composepractice.ui.theme.ComposePracticeTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI / Instrumentation tests for the Calculator screen.
 * Tests full Compose UI rendering, keypad interactions, and arithmetic flows.
 */
@RunWith(AndroidJUnit4::class)
class CalculatorUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Helper to click a keypad button by its label
    private fun clickButton(label: String) {
        composeTestRule.onNode(hasText(label) and hasClickAction()).performClick()
    }

    // ---------- Screen Launch & UI Elements ----------

    @Test
    fun calculatorScreen_opensSuccessfullyAndDisplaysDefaultZero() {
        // Load the calculator UI inside Compose test rule
        composeTestRule.setContent {
            ComposePracticeTheme {
                CalculatorScreen()
            }
        }

        // Verify that default display initial value "0" is displayed
        composeTestRule.onAllNodesWithText("0")[0].assertIsDisplayed()
    }

    @Test
    fun importantCalculatorUiElements_areDisplayedCorrectly() {
        composeTestRule.setContent {
            ComposePracticeTheme {
                CalculatorScreen()
            }
        }

        // Verify all standard calculator keys are displayed on screen
        val allKeys = listOf(
            "C", "±", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "="
        )

        allKeys.forEach { key ->
            composeTestRule.onNode(hasText(key) and hasClickAction()).assertIsDisplayed()
        }
    }

    // ---------- Button Clickability & User Flows ----------

    @Test
    fun numberButtons_areClickableAndDisplayDigits() {
        composeTestRule.setContent {
            ComposePracticeTheme {
                CalculatorScreen()
            }
        }

        // Click numbers 7, 8, 9
        clickButton("7")
        clickButton("8")
        clickButton("9")

        // Verify "789" is rendered in the display
        composeTestRule.onNodeWithText("789").assertIsDisplayed()
    }

    @Test
    fun operatorButtons_areClickable() {
        composeTestRule.setContent {
            ComposePracticeTheme {
                CalculatorScreen()
            }
        }

        // Click number and operator
        clickButton("5")
        clickButton("+")

        // Expression shows "5+"
        composeTestRule.onNodeWithText("5+").assertIsDisplayed()
    }

    @Test
    fun addition_enteringTwoPlusThree_displaysFive() {
        composeTestRule.setContent {
            ComposePracticeTheme {
                CalculatorScreen()
            }
        }

        // Perform 2 + 3 =
        clickButton("2")
        clickButton("+")
        clickButton("3")
        clickButton("=")

        // Display should show result 5
        composeTestRule.onNodeWithText("5").assertIsDisplayed()
    }

    @Test
    fun subtraction_worksCorrectly() {
        composeTestRule.setContent {
            ComposePracticeTheme {
                CalculatorScreen()
            }
        }

        // Perform 9 - 4 = 5
        clickButton("9")
        clickButton("-")
        clickButton("4")
        clickButton("=")

        // Verify result 5 is shown
        composeTestRule.onNodeWithText("5").assertIsDisplayed()
    }

    @Test
    fun multiplication_worksCorrectly() {
        composeTestRule.setContent {
            ComposePracticeTheme {
                CalculatorScreen()
            }
        }

        // Perform 6 × 7 = 42
        clickButton("6")
        clickButton("×")
        clickButton("7")
        clickButton("=")

        // Verify result 42 is shown
        composeTestRule.onNodeWithText("42").assertIsDisplayed()
    }

    @Test
    fun division_worksCorrectly() {
        composeTestRule.setContent {
            ComposePracticeTheme {
                CalculatorScreen()
            }
        }

        // Perform 8 ÷ 2 = 4
        clickButton("8")
        clickButton("÷")
        clickButton("2")
        clickButton("=")

        // Verify result 4 is shown
        composeTestRule.onNodeWithText("4").assertIsDisplayed()
    }

    @Test
    fun decimalInput_worksCorrectly() {
        composeTestRule.setContent {
            ComposePracticeTheme {
                CalculatorScreen()
            }
        }

        // Enter decimal number: 3.5 + 1.5 = 5
        clickButton("3")
        clickButton(".")
        clickButton("5")
        clickButton("+")
        clickButton("1")
        clickButton(".")
        clickButton("5")
        clickButton("=")

        // Verify result 5 is shown
        composeTestRule.onNodeWithText("5").assertIsDisplayed()
    }

    @Test
    fun clearButton_resetsDisplayToZero() {
        composeTestRule.setContent {
            ComposePracticeTheme {
                CalculatorScreen()
            }
        }

        // Enter some digits and then clear
        clickButton("7")
        clickButton("8")
        clickButton("9")
        clickButton("C")

        // Verify screen returns to initial 0 state
        composeTestRule.onAllNodesWithText("0")[0].assertIsDisplayed()
    }

    @Test
    fun percentageButton_worksCorrectly() {
        composeTestRule.setContent {
            ComposePracticeTheme {
                CalculatorScreen()
            }
        }

        // Enter 50 and press % -> 0.5
        clickButton("5")
        clickButton("0")
        clickButton("%")

        // Verify 0.5 is shown
        composeTestRule.onNodeWithText("0.5").assertIsDisplayed()
    }

    @Test
    fun plusMinusButton_togglesNegativeNumber() {
        composeTestRule.setContent {
            ComposePracticeTheme {
                CalculatorScreen()
            }
        }

        // Enter 8 and press ± -> -8
        clickButton("8")
        clickButton("±")

        // Verify -8 is shown
        composeTestRule.onNodeWithText("-8").assertIsDisplayed()
    }

    @Test
    fun divisionByZero_isHandledWithoutCrashing() {
        composeTestRule.setContent {
            ComposePracticeTheme {
                CalculatorScreen()
            }
        }

        // Perform 9 ÷ 0 =
        clickButton("9")
        clickButton("÷")
        clickButton("0")
        clickButton("=")

        // Verify "Error" message is displayed safely without application crash
        composeTestRule.onNodeWithText("Error").assertIsDisplayed()
    }
}
