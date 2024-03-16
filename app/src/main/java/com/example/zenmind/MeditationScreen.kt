package com.example.zenmind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.zenmind.ui.theme.ZenMindTheme
import kotlinx.coroutines.launch

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
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Welcome to Meditation", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Choose your meditation practice:", style = MaterialTheme.typography.body1)
        // Example button to select a meditation type
        Button(onClick = { /* Handle click */ }) {
            Text("Deep Breathing")
        }
        // Add more buttons or interactive elements for different meditation practices
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    ZenMindTheme {
//        MeditationContent()
//    }
//}
