package com.example.zenmind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.zenmind.ui.theme.ZenMindTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardCapitalization
import android.util.Log




import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            Log.d("CurrentUser", "User UID: ${currentUser.uid}")
        } else {
            Log.d("CurrentUser", "User is not logged in")
        }
        // Check if the user is logged in
        if (FirebaseAuth.getInstance().currentUser==null) {
            // User not logged in, redirect to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        setContent {
            ZenMindTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF81C784) // Background color
                ) {
                    ZenMindTheme {
                        HydrationScreen()
                        MeditationScreen()
                    }
                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        // Sign out the user when the activity starts
        FirebaseAuth.getInstance().signOut()
    }
}
