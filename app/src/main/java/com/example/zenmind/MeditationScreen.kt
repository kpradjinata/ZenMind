package com.example.zenmind


import android.content.Intent

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import java.util.*
import com.example.zenmind.ui.theme.ZenMindTheme
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import android.content.Context


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.meditation_screen)

        // Find buttons by their IDs
        val buttonDeepBreathing: Button = findViewById(R.id.buttonDeepBreathing)
        val buttonBodyScan: Button = findViewById(R.id.buttonBodyScan)
        val buttonLovingKindness: Button = findViewById(R.id.buttonLovingKindness)
        val buttonMindfulness: Button = findViewById(R.id.buttonMindfulness)
        val buttonVisualization: Button = findViewById(R.id.buttonVisualization)
        val buttonRandomExercise: Button = findViewById(R.id.buttonRandomExercise)
        val buttonGoBack: Button = findViewById(R.id.goBack)

        // Set click listeners for each button
        buttonDeepBreathing.setOnClickListener {
            startActivity(Intent(this, DeepBreathingScreen::class.java))
            finish()
        }

        buttonBodyScan.setOnClickListener {
            startActivity(Intent(this, BodyScanScreen::class.java))
            finish()
        }

        buttonLovingKindness.setOnClickListener {
            startActivity(Intent(this, LovingKindScreen::class.java))
            finish()
        }

        buttonMindfulness.setOnClickListener {
            startActivity(Intent(this, MindfulScreen::class.java))
            finish()
        }

        buttonVisualization.setOnClickListener {
            startActivity(Intent(this, VisualizationScreen::class.java))
            finish()
        }

        buttonRandomExercise.setOnClickListener {
            startActivity(Intent(this, RandomScreen::class.java))
            finish()
        }

        buttonGoBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

//class MeditationScreen : ComponentActivity() {
//
    val meditationExercises = arrayOf(
        "Mindful Breathing",
        "Body Scan Meditation",
        "Loving-Kindness Meditation",
        "Visualization Meditation",
        "Walking Meditation",
        "Mantra Meditation",
        "Guided Meditation"
    )
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContent {
//            ZenMindTheme {
//                MeditationContent()
//            }
//        }
//    }

@Composable
fun MeditationContent() {
    val context = LocalContext.current
    val titleTextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp // Adjust the font size as needed
    )

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Please select a meditation exercise:",
            style = titleTextStyle,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { displayMeditationRecommendation(context,"Deep Breathing Meditation") }) {
            Text("Deep Breathing")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { displayMeditationRecommendation(context,"Body Scan Meditation") }) {
            Text("Body Scan")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { displayMeditationRecommendation(context,"Loving-Kindness Meditation") }) {
            Text("Loving-Kindness")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { displayMeditationRecommendation(context,"Mindfulness Meditation") }) {
            Text("Mindfulness")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { displayMeditationRecommendation(context, "Visualization Meditation") }) {
            Text("Visualization")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { displayMeditationRecommendation(context, recommendMeditationExercise()) }) {
            Text("Random Exercise")
        }
    }
}
    fun displayMeditationRecommendation(context: Context, recommendation: String) {
        Toast.makeText(context, "Recommended Meditation Exercise: $recommendation", Toast.LENGTH_SHORT).show()
    }

    fun recommendMeditationExercise(): String {
        val randomIndex = Random().nextInt(meditationExercises.size)
        return meditationExercises[randomIndex]

    }



//@Preview
//@Composable
//fun PreviewMeditationContent() {
//    ZenMindTheme {
//        MeditationScreen().MeditationContent()
//    }
//}

