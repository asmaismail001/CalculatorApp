package com.example.composepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxSize
import com.example.composepractice.ui.screens.CalculatorScreen
import com.example.composepractice.ui.theme.ComposePracticeTheme

/**
 * MainActivity — the "View" host.
 *
 * Simple English:
 * This class now does almost nothing except set up the theme and show
 * CalculatorScreen(). All state and calculation logic have moved out —
 * to CalculatorViewModel and CalculatorEngine respectively. This is the
 * MVVM pattern: the Activity's only job is to be a "frame" that hosts
 * the screen; it doesn't own any data itself.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ComposePracticeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    ComposePracticeTheme {
        CalculatorScreen()
    }
}