package com.example.zenmind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.zenmind.ui.theme.ZenMindTheme
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import android.widget.Toast
import java.text.SimpleDateFormat
import android.content.Context
import android.util.Log

import java.util.*

data class UserHydrationData(
    val userId: String = "",
    val weight: Double = 0.0,
    val waterIntakeML: Int = 0,
    val exerciseMinutes: Int = 0,
    val date: String = "" // Added date field
)

@Composable
fun HydrationScreen(userId: String) {
    var weight by remember { mutableStateOf("") }
    var waterIntakeML by remember { mutableStateOf("") }
    var exerciseMinutes by remember { mutableStateOf("") }
    val context = LocalContext.current

    ZenMindTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Weight (kg)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = waterIntakeML,
                onValueChange = { waterIntakeML = it },
                label = { Text("Water Intake (ML)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = exerciseMinutes,
                onValueChange = { exerciseMinutes = it },
                label = { Text("Exercise Minutes") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val currentDate = sdf.format(Date())

                val data = UserHydrationData(
                    userId = userId,
                    weight = weight.toDoubleOrNull() ?: 0.0,
                    waterIntakeML = waterIntakeML.toIntOrNull() ?: 0,
                    exerciseMinutes = exerciseMinutes.toIntOrNull() ?: 0,
                    date = currentDate
                )
                storeHydrationData(context, data)
            }) {
                Text("Submit")
            }
        }
    }
}

fun storeHydrationData(context: Context, data: UserHydrationData) {
    val databaseReference = Firebase.database.reference
    val entryKey = databaseReference.push().key ?: return // Use push().key for unique entry IDs

    Log.d("HydrationData", "Saving data: $data")
    databaseReference.child("users").child(data.userId).child("hydrationData").child(entryKey).setValue(data)
        .addOnSuccessListener {
            Log.d("HydrationData", "Data saved successfully")
            Toast.makeText(context, "Data saved successfully!", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { exception ->
            Log.e("HydrationScreen", "Failed to save data: ${exception.message}", exception)
        }


}
