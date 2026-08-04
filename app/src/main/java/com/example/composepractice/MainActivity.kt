package com.example.composepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composepractice.ui.theme.ComposePracticeTheme

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

@Composable
fun CalculatorScreen() {

    var display by remember { mutableStateOf("0") }

    var expression by remember { mutableStateOf("") }

    var firstNumber by remember { mutableStateOf<Double?>(null) }

    var operator by remember { mutableStateOf("") }

    val buttonRows = listOf(
        listOf("C", "±", "%", "÷"),
        listOf("7", "8", "9", "×"),
        listOf("4", "5", "6", "-"),
        listOf("1", "2", "3", "+"),
        listOf("0", ".", "=")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
            .padding(16.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    Color(0xFF1E1E1E),
                    RoundedCornerShape(30.dp)
                )
                .padding(20.dp),
            contentAlignment = Alignment.BottomEnd
        ) {

            Text(
                text = if (expression.isEmpty()) display else expression,
                color = Color.White,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )

        }

        Spacer(modifier = Modifier.height(16.dp))
        buttonRows.forEachIndexed { rowIndex, row ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.18f),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                row.forEach { text ->

                    val background = when (text) {
                        "C", "±", "%" -> Color.Gray
                        "÷", "×", "-", "+", "=" -> Color(0xFFFF9800)
                        else -> Color(0xFF2C2C2C)
                    }

                    val buttonWeight =
                        if (rowIndex == 4 && text == "0") 2f else 1f

                    Button(
                        onClick = {

                            val result = onButtonClick(
                                currentDisplay = display,
                                currentExpression = expression,
                                pressed = text,
                                firstValue = firstNumber,
                                currentOperator = operator
                            )

                            display = result.display
                            expression = result.expression
                            firstNumber = result.firstValue
                            operator = result.operator

                        },
                        modifier = Modifier
                            .weight(buttonWeight)
                            .fillMaxHeight(),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = background
                        )
                    ) {

                        Text(
                            text = text,
                            color = Color.White,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )

                    }

                }

            }

            Spacer(modifier = Modifier.height(10.dp))

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

data class CalculatorResult(
    val display: String,
    val expression: String,
    val firstValue: Double?,
    val operator: String
)

fun formatNumber(number: Double): String {

    return if (number % 1 == 0.0) {
        number.toInt().toString()
    } else {
        number.toString()
    }

}