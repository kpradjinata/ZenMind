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



import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)


        // Check if the user is logged in
        if (FirebaseAuth.getInstance().currentUser == null) {
            // User not logged in, redirect to LoginScreen
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        } else {
            setContent {
                ZenMindTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                      modifier = Modifier.fillMaxSize(),
                      color = Color(0xFF81C784) // Background color
                ) {
                      HydrationRecommendation()
                    }


                }
            }
        }
    }
}



@Composable
fun HydrationRecommendation() {
    var height by remember { mutableIntStateOf(0) }
    var weight by remember { mutableIntStateOf(0) }
    var activityLevel by remember { mutableFloatStateOf(0.5f) }
    var showDialog by remember { mutableStateOf(false) }
    var recommendedWaterIntake by remember { mutableIntStateOf(0) }



    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Hydration Recommendation",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Height input
        OutlinedTextField(
            value = height.toString(),
            onValueChange = {
                height = it.toIntOrNull() ?: 0
            },
            label = { Text("Height (cm)") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Weight input
        OutlinedTextField(
            value = weight.toString(),
            onValueChange = {
                weight = it.toIntOrNull() ?: 0
            },
            label = { Text("Weight (kg)") },
            keyboardOptions = KeyboardOptions().copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Activity level input
        Slider(
            value = activityLevel,
            onValueChange = {
                activityLevel = it
            },
            valueRange = 0f..1f,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Calculate button
        Button(
            onClick = {
                // Perform hydration recommendation calculation here using height, weight, and activityLevel
                 recommendedWaterIntake =
                    calculateRecommendedWaterIntake(height, weight, activityLevel)
                // Display recommendation
                showDialog = true

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Calculate")
        }

        Spacer(modifier = Modifier.height(16.dp))

        //LaunchedEffect(showDialog) {
            if (showDialog) {
                RecommendationDialog(recommendedWaterIntake) {
                    showDialog = false
                }
            }

       // }
    }
}



@Composable
fun RecommendationDialog(recommendedWaterIntake: Int, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Recommendation") },
        text = { Text("Recommended Water Intake: $recommendedWaterIntake ml") },
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("OK")
            }
        }
    )
}

// Replace this with your hydration recommendation logic
fun calculateRecommendedWaterIntake(height: Int, weight: Int, activityLevel: Float): Int {
    // Simple formula for demonstration purposes
    val baseWaterIntake = (weight / 2.2) * 30 // Convert weight to kg
    val activityMultiplier = when {
        activityLevel < 0.3 -> 0.5
        activityLevel < 0.6 -> 0.7
        else -> 1.0
    }

    return (baseWaterIntake * activityMultiplier).toInt()
}

@Preview(showBackground = true)
@Composable
fun HydrationRecommendationPreview() {
    ZenMindTheme {
        HydrationRecommendation()
    }
}
