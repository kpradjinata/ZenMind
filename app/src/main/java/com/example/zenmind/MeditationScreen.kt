package com.example.zenmind

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.widget.Toast
import kotlin.random.Random

@Composable
fun MeditationScreen() {
    val context = LocalContext.current
    val exercises = listOf(
        "Mindful Breathing",
        "Body Scan Meditation",
        "Loving-Kindness Meditation",
        "Visualization Meditation",
        "Walking Meditation",
        "Mantra Meditation",
        "Guided Meditation"
    )
    var recommendedExercise by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF81C784) // Use your theme or desired background color
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Select a Meditation Exercise:", modifier = Modifier.padding(bottom = 16.dp))
            exercises.forEach { exercise ->
                Button(
                    onClick = { recommendedExercise = exercise },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(exercise)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    recommendedExercise = exercises.random()
                    Toast.makeText(context, "Recommended Meditation Exercise: $recommendedExercise", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Recommend Random Exercise")
            }
            if (recommendedExercise.isNotEmpty()) {
                Text("Recommended: $recommendedExercise", modifier = Modifier.padding(top = 16.dp))
            }
        }
    }
}
