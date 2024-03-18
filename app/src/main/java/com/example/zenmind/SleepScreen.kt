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
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SleepRecommendation() {
    val context = LocalContext.current
    var sleepTime by remember { mutableStateOf<LocalTime?>(null) }
    var wakeUpTime by remember { mutableStateOf<LocalTime?>(null) }
    var showResult by remember { mutableStateOf(false) }
    var lifestyleScore by remember { mutableStateOf(0) }

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
            onClick = {
                showTimePicker(context, sleepTime) { sleepTime = it }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = sleepTime?.format(DateTimeFormatter.ofPattern("h:mm a")) ?: "Set Sleep Time")
        }

        Button(
            onClick = {
                showTimePicker(context, wakeUpTime) { wakeUpTime = it }
            },
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
                    addSleepScoreToFirebase(calculatedScore, context) // Directly store the score in Firebase
                } else {
                    Toast.makeText(context, "Please set both sleep and wake-up times", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Check")
        }

        if (showResult) {
            Text(
                text = getPersonalizedMessage(lifestyleScore),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
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

fun getPersonalizedMessage(score: Int):String {
    // Your personalized message logic based on the score
    return "Score: $score"
}

fun showTimePicker(context: android.content.Context, currentTime: LocalTime?, onTimeSet: (LocalTime) -> Unit) {
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute -> onTimeSet(LocalTime.of(hourOfDay, minute)) },
        currentTime?.hour ?: LocalTime.now().hour,
        currentTime?.minute ?: LocalTime.now().minute,
        false // Use 24-hour view
    )
    timePickerDialog.show()
}

fun addSleepScoreToFirebase(score: Int, context: android.content.Context) {
    CoroutineScope(Dispatchers.IO).launch {
        val databaseRef = Firebase.database.reference.child("users").child("defaultUserId").child("sleepData")
        val scoreEntry = mapOf(
            "date" to DateTimeFormatter.ISO_DATE.format(java.time.LocalDate.now()),
            "score" to score
        )
        databaseRef.push().setValue(scoreEntry)
            .addOnSuccessListener {
                Toast.makeText(context, "Sleep data saved successfully.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to save sleep data.", Toast.LENGTH_SHORT).show()
            }
    }
}

