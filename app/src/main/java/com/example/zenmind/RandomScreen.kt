package com.example.zenmind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import androidx.webkit.WebViewCompat
import androidx.webkit.WebViewFeature
import android.webkit.WebViewClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.TextView
import java.util.Random

class RandomScreen : AppCompatActivity() {
    private val meditationExercises = arrayOf(
        "Deep Breathing Meditation",
        "Body Scan Meditation",
        "Loving-Kindness Meditation",
        "Mindfulness Meditation",
        "Visualization Meditation"
        // Add more meditation exercises as needed
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        //private val videoUrl = "https://www.youtube.com/watch?v=V2KCAfHjySQ"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_screen)

        val exerciseText: TextView = findViewById(R.id.exerciseText)

        // Generate a random index
        val randomIndex = Random().nextInt(meditationExercises.size)

        // Retrieve the exercise corresponding to the random index
        val randomExercise = meditationExercises[randomIndex]

        // Set the exercise text on the screen
        exerciseText.text = randomExercise
        /*val webView = findViewById<WebView>(R.id.webView)

        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        webView.webViewClient = WebViewClient()
        webView.loadUrl(videoUrl)*/

        val finishExercise: Button = findViewById(R.id.finishExercise)
        finishExercise.setOnClickListener {
            // Finish meditation
            startActivity(Intent(this, MeditationScreen::class.java))
            finish()
        }
    }
}