package com.example.composepractice.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composepractice.viewmodel.CalculatorViewModel


@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = viewModel()
) {

    val state by viewModel.state.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()

    val buttonRows = listOf(
        listOf("C", "±", "%", "÷"),
        listOf("7", "8", "9", "×"),
        listOf("4", "5", "6", "-"),
        listOf("1", "2", "3", "+"),
        listOf("0", ".", "=")
    )

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val displayFontSize = if (isLandscape) 36.sp else 48.sp
    val buttonFontSize = if (isLandscape) 18.sp else 26.sp

    // ---------- Color palettes ----------
    val screenBackground = if (isDarkTheme) Color(0xFF101010) else Color(0xFFF2F2F2)
    val displayBackground = if (isDarkTheme) Color(0xFF1E1E1E) else Color(0xFFFFFFFF)
    val displayTextColor = if (isDarkTheme) Color.White else Color(0xFF101010)
    val functionButtonBg = if (isDarkTheme) Color.Gray else Color(0xFFD6D6D6)
    val numberButtonBg = if (isDarkTheme) Color(0xFF2C2C2C) else Color(0xFFE8E8E8)
    val numberButtonTextColor = if (isDarkTheme) Color.White else Color(0xFF101010)
    val operatorButtonBg = Color(0xFFFF9800)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBackground)
            .padding(16.dp)
    ) {

        // Theme toggle row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = if (isDarkTheme) "🌙" else "☀️",
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.width(8.dp))

            Switch(
                checked = isDarkTheme,
                onCheckedChange = { viewModel.toggleTheme() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = operatorButtonBg,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color.Gray
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.3f)
                .background(
                    displayBackground,
                    RoundedCornerShape(30.dp)
                )
                .padding(20.dp),
            contentAlignment = Alignment.BottomEnd
        ) {

            Text(
                text = if (state.expression.isEmpty()) state.display else state.expression,
                color = displayTextColor,
                fontSize = displayFontSize,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2.5f)
        ) {

            buttonRows.forEachIndexed { rowIndex, row ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    row.forEach { text ->

                        val background = when (text) {
                            "C", "±", "%" -> functionButtonBg
                            "÷", "×", "-", "+", "=" -> operatorButtonBg
                            else -> numberButtonBg
                        }

                        val textColor = when (text) {
                            "÷", "×", "-", "+", "=" -> Color.White
                            "C", "±", "%" -> if (isDarkTheme) Color.White else Color(0xFF101010)
                            else -> numberButtonTextColor
                        }

                        val buttonWeight =
                            if (rowIndex == buttonRows.lastIndex && text == "0") 2f else 1f

                        Button(
                            onClick = { viewModel.onButtonClick(text) },
                            modifier = Modifier
                                .weight(buttonWeight)
                                .fillMaxHeight(),
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = background
                            )
                        ) {
                            Text(
                                text = text,
                                color = textColor,
                                fontSize = buttonFontSize,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}