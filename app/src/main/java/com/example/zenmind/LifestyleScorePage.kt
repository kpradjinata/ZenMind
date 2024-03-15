package com.example.zenmind

import android.util.Log
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
import androidx.compose.runtime.collectAsState

class LifestyleViewModel : ViewModel() {
    private val _sleepScores = MutableStateFlow<List<Int>>(emptyList())
    val sleepScores: StateFlow<List<Int>> = _sleepScores

    private val _meditationScores = MutableStateFlow<List<Int>>(emptyList())
    val meditationScores: StateFlow<List<Int>> = _meditationScores

    private val _hydrationScores = MutableStateFlow<List<Int>>(emptyList())
    val hydrationScores: StateFlow<List<Int>> = _hydrationScores

    private val _isUpdateComplete = MutableStateFlow(false)
    val isUpdateComplete: StateFlow<Boolean> = _isUpdateComplete

    fun addSleepScore(score: Int) {
        viewModelScope.launch {
            _sleepScores.value = _sleepScores.value + listOf(score)
            _isUpdateComplete.value = true // Signal that update is complete
        }
    }
    fun resetUpdateState() {
        _isUpdateComplete.value = false
    }

    fun addMeditationScore(score: Int) {
        viewModelScope.launch {
            _meditationScores.value = _meditationScores.value + score
        }
    }

    fun addHydrationScore(score: Int) {
        viewModelScope.launch {
            _hydrationScores.value = _hydrationScores.value + score
        }
    }
}

@Composable
fun LifestyleScorePage(viewModel: LifestyleViewModel = viewModel()) {
    // Observing scores from the ViewModel
    val sleepScores by viewModel.sleepScores.collectAsState()
    Log.d("LifestyleScorePage", "Sleep Scores: $sleepScores")
    val meditationScores by viewModel.meditationScores.collectAsState()
    val hydrationScores by viewModel.hydrationScores.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Log.d("LifestyleScorePage", "Displaying updated scores")
        Text("Lifestyle Scores", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        // Use .joinToString() for better display of List<Int>
        Text("Sleep Scores: ${sleepScores.joinToString()}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(4.dp))
        Text("Meditation Scores: ${meditationScores.joinToString()}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(4.dp))
        Text("Hydration Scores: ${hydrationScores.joinToString()}", style = MaterialTheme.typography.bodyLarge)
    }
}