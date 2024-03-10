package com.example.zenmind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.zenmind.ui.theme.ZenMindTheme
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZenMindTheme {
                // Apply a soothing background color
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HydrationRecommendationEnhanced()
                }
            }
        }
    }
}

@Composable
fun HydrationRecommendationEnhanced() {
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var activityLevel by remember { mutableStateOf(0.5f) }
    var showDialog by remember { mutableStateOf(false) }
    var recommendedWaterIntake by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Hydration Recommendation",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Height (cm)") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(8.dp)
        )

        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight (kg)") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(8.dp)
        )

        Slider(
            value = activityLevel,
            onValueChange = { activityLevel = it },
            valueRange = 0f..1f,
            modifier = Modifier.fillMaxWidth(),
            steps = 2
        )
        Text(
                text = when (activityLevel) {
                    in 0f..0.33f -> "Low"
                    in 0.34f..0.66f -> "Medium"
                    else -> "High"
                },
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.align(Alignment.End)
        )

        Button(
            onClick = {
                val heightValue = height.toIntOrNull()
                val weightValue = weight.toIntOrNull()
                if (heightValue != null && weightValue != null) {
                    recommendedWaterIntake = calculateRecommendedWaterIntake(heightValue, weightValue, activityLevel)
                    showDialog = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Calculate")
        }

        AnimatedVisibility(visible = showDialog) {
            RecommendationResultCard(recommendedWaterIntake)
        }
    }
}


@Composable
fun RecommendationResultCard(recommendedWaterIntake: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Recommended Water Intake", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("$recommendedWaterIntake ml", style = MaterialTheme.typography.headlineSmall)
        }
    }
}

fun calculateRecommendedWaterIntake(height: Int, weight: Int, activityLevel: Float): Int {
    val baseWaterIntake = (weight / 2.2) * 30
    val activityMultiplier = when {
        activityLevel < 0.3 -> 0.5
        activityLevel < 0.6 -> 0.7
        else -> 1.0
    }
    return (baseWaterIntake * activityMultiplier).toInt()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ZenMindTheme {
        HydrationRecommendationEnhanced()
    }
}
