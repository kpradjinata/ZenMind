package com.example.zenmind

import android.util.Log
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun SleepRecommendation(viewModel: LifestyleViewModel = viewModel()) {
    val context = LocalContext.current
    var sleepTime by remember { mutableStateOf<LocalTime?>(null) }
    var wakeUpTime by remember { mutableStateOf<LocalTime?>(null) }
    var showResult by remember { mutableStateOf(false) }
    var lifestyleScore by remember { mutableStateOf(0) }

    fun showTimePicker(currentTime: LocalTime?, onTimeSet: (LocalTime) -> Unit) {
        val timePickerDialog = TimePickerDialog(context,
            { _, hourOfDay, minute -> onTimeSet(LocalTime.of(hourOfDay, minute)) },
            currentTime?.hour ?: LocalTime.now().hour,
            currentTime?.minute ?: LocalTime.now().minute,
            false // Use 24-hour view
        )
        timePickerDialog.show()
    }

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

        Button(
            onClick = { showTimePicker(sleepTime) { sleepTime = it } },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = sleepTime?.format(DateTimeFormatter.ofPattern("h:mm a")) ?: "Set Sleep Time")
        }

        Button(
            onClick = { showTimePicker(wakeUpTime) { wakeUpTime = it } },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = wakeUpTime?.format(DateTimeFormatter.ofPattern("h:mm a")) ?: "Set Wake-up Time")
        }

        Button(
            onClick = {
                if (sleepTime != null && wakeUpTime != null) {
                    showResult = true
                    val calculatedScore = calculateLifestyleScore(sleepTime!!, wakeUpTime!!)
                    lifestyleScore = calculatedScore
                    viewModel.addSleepScore(calculatedScore)
                    Toast.makeText(context, "Lifestyle score updated", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Please set both sleep and wake-up times", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Check")
        }

        if (showResult) {
            val message = getPersonalizedMessage(lifestyleScore)
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

fun getPersonalizedMessage(score: Int): String {
    return when (score) {
        100 -> "Adequate sleep achieved! Keep up the good work. Lifestyle score earned from Sleep: $score"
        10 -> "Careful! Lack of sleep can impact cognitive function and decision making, you need to get more sleep in the coming days. Lifestyle score earned from Sleep: $score"
        40 -> "3-4 hours of sleep is still insufficient but at the very least you are entering the REM cycle, look to get more sleep in the coming days. Lifestyle score earned from Sleep: $score"
        75 -> "Almost adequate sleep achieved. Consider taking a power nap during the day. Lifestyle score earned from Sleep: $score"
        90 -> "The effects of sleeping more than 9 hours a day are heavily debated, though it would only be considered detrimental if you were massively oversleeping (13+ hours). Lifestyle score earned from Sleep: $score"
        else -> "Your sleep score is $score. It's important to aim for 7-9 hours of quality sleep each night."
    }
}

fun calculateLifestyleScore(sleepTime: LocalTime, wakeUpTime: LocalTime): Int {
    val duration = ChronoUnit.HOURS.between(sleepTime, wakeUpTime).let { if (it < 0) it + 24 else it }.toInt()

    return when (duration) {
        in 0..2 -> 10
        in 3..4 -> 40
        in 5..6 -> 75
        in 7..9 -> 100
        else -> 90
    }
}