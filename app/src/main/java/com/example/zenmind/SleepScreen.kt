package com.example.zenmind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.zenmind.ui.theme.ZenMindTheme
import androidx.compose.ui.unit.dp

@Composable
fun SleepRecommendation() {
    var sleepTime by remember { mutableStateOf("") }
    var wakeUpTime by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var showResult by remember { mutableStateOf(false) }
    var adequateSleep by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Sleep Recommendation",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date (YYYY-MM-DD)") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = sleepTime,
                onValueChange = { sleepTime = it },
                label = { Text("Sleep Time (HH:MM AM/PM)") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            OutlinedTextField(
                value = wakeUpTime,
                onValueChange = { wakeUpTime = it },
                label = { Text("Wake-up Time (HH:MM AM/PM)") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }

        Button(
            onClick = {
                showResult = true
                adequateSleep = isSleepAdequate(sleepTime, wakeUpTime)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Check")
        }

        if (showResult) {
            Text(
                text = if (adequateSleep) "Adequate sleep achieved! X Lifestyle points earned from Sleep" else "Your sleep was not adequate.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

fun isSleepAdequate(sleepTime: String, wakeUpTime: String): Boolean {
    val sleepRegex = """(\d{1,2}):(\d{2})\s?(AM|PM)""".toRegex()
    val wakeUpRegex = """(\d{1,2}):(\d{2})\s?(AM|PM)""".toRegex()

    val sleepMatch = sleepRegex.find(sleepTime)
    val wakeUpMatch = wakeUpRegex.find(wakeUpTime)

    if (sleepMatch != null && wakeUpMatch != null) {
        val (sleepHour, sleepMinute, sleepPeriod) = sleepMatch.destructured
        val (wakeUpHour, wakeUpMinute, wakeUpPeriod) = wakeUpMatch.destructured

        var sleepHourInt = sleepHour.toInt()
        var wakeUpHourInt = wakeUpHour.toInt()

        // Convert to 24-hour format
        if (sleepPeriod == "PM" && sleepHourInt < 12) {
            sleepHourInt += 12
        }
        if (wakeUpPeriod == "PM" && wakeUpHourInt < 12) {
            wakeUpHourInt += 12
        }

        val totalSleepHours = when {
            wakeUpHourInt > sleepHourInt -> wakeUpHourInt - sleepHourInt
            wakeUpHourInt == sleepHourInt && wakeUpMinute.toInt() > sleepMinute.toInt() -> 0
            else -> 24 - sleepHourInt + wakeUpHourInt
        }

        val totalSleepMinutes = when {
            wakeUpMinute.toInt() >= sleepMinute.toInt() -> wakeUpMinute.toInt() - sleepMinute.toInt()
            else -> 60 - sleepMinute.toInt() + wakeUpMinute.toInt()
        }

        val totalSleep = totalSleepHours + totalSleepMinutes / 60.0

        return totalSleep in 7.0..9.0
    }

    return false
}

@Preview(showBackground = true)
@Composable
fun PreviewSleepRecommendation() {
    ZenMindTheme {
        SleepRecommendation()
    }
}