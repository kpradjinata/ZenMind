package com.example.zenmind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.zenmind.ui.theme.ZenMindTheme
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HydrationRecommendationEnhanced(viewModel: LifestyleViewModel = viewModel()) {
    var weightInput by remember { mutableStateOf("") }
    var waterIntake by remember { mutableStateOf("") }
    var climate by remember { mutableStateOf("Cool/Temperate") }
    var minutesOfExercise by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var recommendedWaterIntake by remember { mutableStateOf(0) }
    var lifestyleScore by remember { mutableStateOf(0) }
    var displayScoreMessage by remember { mutableStateOf(false) }

    val climateOptions = listOf("Cool/Temperate", "Hot", "Humid and Hot")

    Column(modifier = Modifier.padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Hydration Recommendation", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.align(Alignment.CenterHorizontally))

        OutlinedTextField(value = weightInput, onValueChange = { weightInput = it }, label = { Text("Weight (e.g., 150 lbs or 68 kg)") }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next), modifier = Modifier.fillMaxWidth(), singleLine = true)

        OutlinedTextField(value = waterIntake, onValueChange = { waterIntake = it }, label = { Text("mL of Water Drank Today") }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next), modifier = Modifier.fillMaxWidth(), singleLine = true)

        Text("Select Climate:", style = MaterialTheme.typography.bodyMedium)
        Row {
            climateOptions.forEach { option ->
                Text(text = option, modifier = Modifier.padding(8.dp).clickable { climate = option }, style = MaterialTheme.typography.bodyLarge.copy(color = if (climate == option) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface))
            }
        }

        OutlinedTextField(value = minutesOfExercise, onValueChange = { minutesOfExercise = it }, label = { Text("Minutes of Exercise") }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done), modifier = Modifier.fillMaxWidth(), singleLine = true)

        Button(onClick = {
            val (weightValue, weightUnit) = parseWeightInput(weightInput)
            val exerciseMinutes = minutesOfExercise.toIntOrNull() ?: 0
            val waterIntakeValue = waterIntake.toIntOrNull()
            if (weightValue != null && waterIntakeValue != null && weightUnit != null) {
                recommendedWaterIntake = calculateRecommendedWaterIntake(weightValue, weightUnit, climate, exerciseMinutes)
                showDialog = true
                lifestyleScore = calculateHydrationScore(recommendedWaterIntake, waterIntakeValue)
                displayScoreMessage = true
                viewModel.addHydrationScore(lifestyleScore)
            }
        }, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), shape = RoundedCornerShape(8.dp)) {
            Text("Calculate")
        }

        if (showDialog) {
            RecommendationResultCard(recommendedWaterIntake)
        }

        if (displayScoreMessage) {
            val message = getHydrationMessage(lifestyleScore)
            Text(text = message, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(top = 16.dp))
        }
    }
}


fun parseWeightInput(weightInput: String): Pair<Double?, String?> {
    val weightValue = weightInput.filter { it.isDigit() || it == '.' }.toDoubleOrNull()
    val weightUnit = if ("lbs" in weightInput.lowercase()) "lbs" else if ("kg" in weightInput.lowercase()) "kg" else null
    return weightValue to weightUnit
}

fun calculateRecommendedWaterIntake(weight: Double, unit: String, climate: String, minutesOfExercise: Int): Int {
    val weightInLbs = if (unit == "kg") weight * 2.20462 else weight
    var waterIntakeOz = weightInLbs / 2.0
    var waterIntakeMl = waterIntakeOz * 20


    waterIntakeMl += when (climate) {
        "Hot" -> 300.0
        "Humid and Hot" -> 500.0
        else -> 0.0
    }


    val additionalWaterPer30Min = when (climate) {
        "Cool/Temperate" -> 300.0
        "Hot" -> 350.0
        "Humid and Hot" -> 400.0
        else -> 300.0
    }

    val exerciseAdjustment = (minutesOfExercise / 30) * additionalWaterPer30Min
    waterIntakeMl += exerciseAdjustment

    return waterIntakeMl.toInt()
}
fun calculateHydrationScore(recommendedIntake: Int, actualIntake: Int): Int {
    return when {
        actualIntake >= recommendedIntake -> 100
        actualIntake >= recommendedIntake * 0.75 -> 75
        actualIntake >= recommendedIntake * 0.5 -> 40
        actualIntake < recommendedIntake * 0.5 -> 10
        else -> 0
    }
}

fun getHydrationMessage(score: Int): String {
    return when (score) {
        100 -> "Adequate water intake achieved! Keep up the good work. Lifestyle score earned from Hydration: $score"
        75 -> "Almost adequate water intake achieved. Consider drinking a little more throughout the day. Lifestyle score earned from Hydration: $score"
        40 -> "Your water intake is below 50% of the recommended amount. It's important to drink more water. Lifestyle score earned from Hydration: $score"
        10 -> "Careful! Your water intake is significantly below the recommended amount. Staying hydrated is crucial for your health. Lifestyle score earned from Hydration: $score"
        else -> "It's important to aim for adequate hydration each day."
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ZenMindTheme {
        HydrationRecommendationEnhanced(viewModel = viewModel())
    }
}