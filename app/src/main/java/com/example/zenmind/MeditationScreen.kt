package com.example.zenmind

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.content.Context

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import com.example.zenmind.ui.theme.ZenMindTheme
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import java.util.*

class MeditationScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZenMindTheme {
                MeditationContent()
            }
        }
    }
}

@Composable
fun MeditationContent() {
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Select a Meditation Exercise",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
        )
        Button(onClick = { context.startActivity(Intent(context, DeepBreathingScreen::class.java)) }) {
            Text("Deep Breathing")
        }
        Button(onClick = { context.startActivity(Intent(context, BodyScanScreen::class.java)) }) {
            Text("Body Scan")
        }
        Button(onClick = { context.startActivity(Intent(context, LovingKindScreen::class.java)) }) {
            Text("Loving Kindness")
        }
        Button(onClick = { context.startActivity(Intent(context, MindfulScreen::class.java)) }) {
            Text("Mindfulness")
        }
        Button(onClick = { context.startActivity(Intent(context, VisualizationScreen::class.java)) }) {
            Text("Visualization")
        }
        // Assuming RandomScreen is a placeholder for actual functionality
        Button(onClick = { showRandomExercise(context) }) {
            Text("Random Exercise")
        }
        Button(onClick = { context.startActivity(Intent(context, MainActivity::class.java)) }) {
            Text("Go Back")
        }
    }
}

fun showRandomExercise(context: Context) {
    val exercises = arrayOf(
        Intent(context, DeepBreathingScreen::class.java),
        Intent(context, BodyScanScreen::class.java),
        // Add other exercises here
    )
    val randomIndex = Random().nextInt(exercises.size)
    context.startActivity(exercises[randomIndex])
}
