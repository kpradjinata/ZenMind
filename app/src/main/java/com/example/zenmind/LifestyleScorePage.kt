package com.example.zenmind

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import androidx.compose.runtime.collectAsState
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import android.util.Log


// ViewModel to fetch hydration scores from Firebase

data class HydrationRecord(
    val date: String = "",
    val exerciseMinutes: Int = 0,
    val waterIntakeML: Int = 0,
    val weight: Double = 0.0
)

data class SleepRecord(
    val date: String = "",
    val score: Int = 0
)

class LifestyleViewModel : ViewModel() {
    private val _hydrationRecords = MutableStateFlow<List<HydrationRecord>>(emptyList())
    val hydrationRecords: StateFlow<List<HydrationRecord>> = _hydrationRecords

    private val _sleepRecords = MutableStateFlow<List<SleepRecord>>(emptyList())
    val sleepRecords: StateFlow<List<SleepRecord>> = _sleepRecords

    init {
        fetchHydrationRecords()
        fetchSleepRecords()
    }

    private fun fetchHydrationRecords() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val snapshot = Firebase.database.reference
                    .child("users")
                    .child("defaultUserId")
                    .child("hydrationData")
                    .get().await()

                val records = snapshot.children.mapNotNull { childSnapshot ->
                    childSnapshot.getValue(HydrationRecord::class.java)
                }
                _hydrationRecords.value = records
            } catch (e: Exception) {
                Log.e("LifestyleVM", "Error fetching hydration records", e)
            }
        }
    }

    private fun fetchSleepRecords() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val snapshot = Firebase.database.reference
                    .child("users")
                    .child("defaultUserId")
                    .child("sleepData")
                    .get().await()

                val records = snapshot.children.mapNotNull { childSnapshot ->
                    childSnapshot.getValue(SleepRecord::class.java)
                }
                _sleepRecords.value = records
            } catch (e: Exception) {
                Log.e("LifestyleVM", "Error fetching sleep records", e)
            }
        }
    }
}

@Composable
fun LifestyleScorePage(viewModel: LifestyleViewModel = viewModel()) {
    val hydrationRecords by viewModel.hydrationRecords.collectAsState()
    val sleepRecords by viewModel.sleepRecords.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = "Lifestyle Scores",
            style = MaterialTheme.typography.headlineLarge // Larger and inherently bolder than headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))


        val overallHealthScore = calculateOverallHealthScore(hydrationRecords, sleepRecords)
        Text("Overall Health Score: $overallHealthScore", style = MaterialTheme.typography.headlineSmall)


        hydrationRecords.forEach { record ->
            Text("Date: ${record.date}", style = MaterialTheme.typography.bodyLarge)
            Text("Exercise Minutes: ${record.exerciseMinutes}", style = MaterialTheme.typography.bodyLarge)
            Text("Water Intake (ML): ${record.waterIntakeML}", style = MaterialTheme.typography.bodyLarge)
            Text("Weight: ${record.weight}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
        }

//        sleepRecords.forEach { record ->
//            Text("Date: ${record.date}", style = MaterialTheme.typography.bodyLarge)
//            Text("Sleep Hours: ${record.score}", style = MaterialTheme.typography.bodyLarge)
//            Spacer(modifier = Modifier.height(8.dp))
//        }

        // Optional: Add overall health assessment logic here
    }

}


fun calculateOverallHealthScore(hydrationRecords: List<HydrationRecord>, sleepRecords: List<SleepRecord>): Int {
    // Assuming simple averages for demonstration; replace with your own logic
    val averageWaterIntake = hydrationRecords.map { it.waterIntakeML }.average()
    val averageExerciseMinutes = hydrationRecords.map { it.exerciseMinutes }.average()
    val averageSleepScore = sleepRecords.map { it.score }.average()

    // Simplified scoring logic
    val waterScore = if (averageWaterIntake >= 2500) 25 else 0
    val exerciseScore = if (averageExerciseMinutes >= 30) 25 else 0
    val sleepScore = if (averageSleepScore >= 75) 50 else 0

    return waterScore + exerciseScore + sleepScore
}