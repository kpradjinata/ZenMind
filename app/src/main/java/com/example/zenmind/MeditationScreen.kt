package com.example.zenmind

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

// Define a constant for the SharedPreferences key
private const val PREFS_KEY_START_TIME = "start_time"

class MeditationScreen : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private var startTime: Long = 0L
    private var timeSpent: Long = 0L
    private var totalTime: Long = 0L
    private lateinit var timeSpentTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.meditation_screen)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Initialize timeSpentTextView
        timeSpentTextView = findViewById(R.id.timeSpentTextView)

        // Restore startTime from SharedPreferences
        startTime = sharedPreferences.getLong(PREFS_KEY_START_TIME, 0L)

        // Find buttons by their IDs
        val buttonDeepBreathing: Button = findViewById(R.id.buttonDeepBreathing)
        val buttonBodyScan: Button = findViewById(R.id.buttonBodyScan)
        val buttonLovingKindness: Button = findViewById(R.id.buttonLovingKindness)
        val buttonMindfulness: Button = findViewById(R.id.buttonMindfulness)
        val buttonVisualization: Button = findViewById(R.id.buttonVisualization)
        val buttonGoBack: Button = findViewById(R.id.goBack)

        // Set click listeners for each button
        buttonDeepBreathing.setOnClickListener {
            // Store the start time when the button is clicked
            startTime = System.currentTimeMillis()
            // Save startTime to SharedPreferences
            sharedPreferences.edit().putLong(PREFS_KEY_START_TIME, startTime).apply()
            // Start the DeepBreathingScreen activity
            startMeditation(DeepBreathingScreen::class.java)
        }

        buttonBodyScan.setOnClickListener {
            // Store the start time when the button is clicked
            startTime = System.currentTimeMillis()
            // Save startTime to SharedPreferences
            sharedPreferences.edit().putLong(PREFS_KEY_START_TIME, startTime).apply()
            startMeditation(BodyScanScreen::class.java)
        }

        buttonLovingKindness.setOnClickListener {
            // Store the start time when the button is clicked
            startTime = System.currentTimeMillis()
            // Save startTime to SharedPreferences
            sharedPreferences.edit().putLong(PREFS_KEY_START_TIME, startTime).apply()
            startMeditation(LovingKindScreen::class.java)
        }

        buttonMindfulness.setOnClickListener {
            // Store the start time when the button is clicked
            startTime = System.currentTimeMillis()
            // Save startTime to SharedPreferences
            sharedPreferences.edit().putLong(PREFS_KEY_START_TIME, startTime).apply()
            startMeditation(MindfulScreen::class.java)
        }

        buttonVisualization.setOnClickListener {
            // Store the start time when the button is clicked
            startTime = System.currentTimeMillis()
            // Save startTime to SharedPreferences
            sharedPreferences.edit().putLong(PREFS_KEY_START_TIME, startTime).apply()
            startMeditation(VisualizationScreen::class.java)
        }

        buttonGoBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    // Function to start meditation exercise activity
    private fun startMeditation(activityClass: Class<*>) {
        startActivity(Intent(this, activityClass))
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "startTime HERE: $startTime")
        // Calculate time spent when the user returns to the meditation screen
        if (startTime != 0L) {
            val currentTime = System.currentTimeMillis()
            timeSpent += currentTime - startTime
            // Reset start time for next measurement
            startTime = 0L
            // Update the TextView with the elapsed time
            updateElapsedTime()
        }
    }

    private fun updateElapsedTime() {
        val minutes = (timeSpent / 1000) / 60
        val seconds = (timeSpent / 1000) % 60
        val formattedTime = String.format("%02d:%02d", minutes, seconds)
        timeSpentTextView.text = "Meditation Time Spent: $formattedTime"
    }
}