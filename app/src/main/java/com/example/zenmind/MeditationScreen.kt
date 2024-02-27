package com.example.zenmind

import android.widget.Toast
import java.util.*
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.zenmind.ui.theme.ZenMindTheme

class MeditationScreen : AppCompatActivity() {

    // Array of meditation exercises for random ideas
    private val meditationExercises = arrayOf(
        "Mindful Breathing",
        "Body Scan Meditation", // Body Scan Meditation: Involves systematically scanning the body for tension and releasing it.
        "Loving-Kindness Meditation", // Loving-Kindness Meditation: Cultivates feelings of love, compassion, and goodwill towards oneself and others.
        "Visualization Meditation", // Visualization Meditation: Utilizes mental imagery to evoke a sense of relaxation and positivity.
        "Walking Meditation",
        "Mantra Meditation",
        "Guided Meditation"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.zenmind.R.xml.meditation_screen)

        // Find buttons by their IDs
        val buttonDeepBreathing: Button = findViewById(R.id.buttonDeepBreathing)
        val buttonBodyScan: Button = findViewById(R.id.buttonBodyScan)
        val buttonLovingKindness: Button = findViewById(R.id.buttonLovingKindness)
        val buttonMindfulness: Button = findViewById(R.id.buttonMindfulness)
        val buttonVisualization: Button = findViewById(R.id.buttonVisualization)
        val buttonRandomExercise: Button = findViewById(R.id.buttonRandomExercise)

        // Set click listeners for each button
        buttonDeepBreathing.setOnClickListener {
            displayMeditationRecommendation("Deep Breathing Meditation")
        }

        buttonBodyScan.setOnClickListener {
            displayMeditationRecommendation("Body Scan Meditation")
        }

        buttonLovingKindness.setOnClickListener {
            displayMeditationRecommendation("Loving-Kindness Meditation")
        }

        buttonMindfulness.setOnClickListener {
            displayMeditationRecommendation("Mindfulness Meditation")
        }

        buttonVisualization.setOnClickListener {
            displayMeditationRecommendation("Visualization Meditation")
        }

        // Set click listener for the "Random Exercise" button
        buttonRandomExercise.setOnClickListener {
            val recommendedExercise = recommendMeditationExercise()
            displayMeditationRecommendation(recommendedExercise)
        }
    }

    // Function to display the selected meditation recommendation
    private fun displayMeditationRecommendation(recommendation: String) {
        // Show a toast message with the selected meditation recommendation
        Toast.makeText(this, "Recommended Meditation Exercise: $recommendation", Toast.LENGTH_SHORT).show()
    }

    // Method to recommend a random meditation exercise
    private fun recommendMeditationExercise(): String {
        // Generate a random index within the range of the array length
        val randomIndex = Random().nextInt(meditationExercises.size)

        // Return the recommended meditation exercise at the random index
        return meditationExercises[randomIndex]
    }
}