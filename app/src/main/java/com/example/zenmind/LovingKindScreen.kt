package com.example.zenmind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.webkit.WebViewCompat
import androidx.webkit.WebViewFeature
import android.webkit.WebViewClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Button
import android.content.Intent

class LovingKindScreen : AppCompatActivity() {
    //private val videoUrl = "https://www.youtube.com/watch?v=V2KCAfHjySQ"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loving_kind_screen)
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