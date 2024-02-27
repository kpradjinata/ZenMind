/*
// Buttons

<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:padding="16dp"
        tools:context=".MainActivity">

<Button
        android:id="@+id/buttonDeepBreathing"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Deep Breathing Meditation"
                android:layout_marginBottom="16dp"/>

<Button
        android:id="@+id/buttonBodyScan"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Body Scan Meditation"
                android:layout_below="@id/buttonDeepBreathing"
                android:layout_marginBottom="16dp"/>

<Button
        android:id="@+id/buttonLovingKindness"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Loving-Kindness Meditation"
                android:layout_below="@id/buttonBodyScan"
                android:layout_marginBottom="16dp"/>

<Button
        android:id="@+id/buttonMindfulness"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Mindfulness Meditation"
                android:layout_below="@id/buttonLovingKindness"
                android:layout_marginBottom="16dp"/>

<Button
        android:id="@+id/buttonVisualization"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Visualization Meditation"
                android:layout_below="@id/buttonMindfulness"/>

</RelativeLayout>
*/
//********************************************************************************************
//User will select meditation exercise type

import android.widget.Toast;
import java.util.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Array of meditation exercises for random ideas
    private static String[] meditationExercises = {
            "Mindful Breathing",
            "Body Scan Meditation", // Body Scan Meditation: Involves systematically scanning the body for tension and releasing it.
            "Loving-Kindness Meditation", // Loving-Kindness Meditation: Cultivates feelings of love, compassion, and goodwill towards oneself and others.
            "Visualization Meditation", // Visualization Meditation: Utilizes mental imagery to evoke a sense of relaxation and positivity.
            "Walking Meditation",
            "Mantra Meditation",
            "Guided Meditation"
    };

    // Function to display the selected meditation recommendation
    private void displayMeditationRecommendation(String recommendation) {
        // Show a toast message with the selected meditation recommendation
        Toast.makeText(this, "Recommended Meditation Exercise: " + recommendation, Toast.LENGTH_SHORT).show();
    }

    // Method to recommend a random meditation exercise
    private String recommendMeditationExercise() {
        // Generate a random index within the range of the array length
        Random random = new Random();
        int randomIndex = random.nextInt(meditationExercises.length);

        // Return the recommended meditation exercise at the random index
        return meditationExercises[randomIndex];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find buttons by their IDs
        Button buttonDeepBreathing = findViewById(R.id.buttonDeepBreathing);
        Button buttonBodyScan = findViewById(R.id.buttonBodyScan);
        Button buttonLovingKindness = findViewById(R.id.buttonLovingKindness);
        Button buttonMindfulness = findViewById(R.id.buttonMindfulness);
        Button buttonVisualization = findViewById(R.id.buttonVisualization);

        // Set click listeners for each button
        buttonDeepBreathing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayMeditationRecommendation("Deep Breathing Meditation");
            }
        });

        buttonBodyScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayMeditationRecommendation("Body Scan Meditation");
            }
        });

        buttonLovingKindness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayMeditationRecommendation("Loving-Kindness Meditation");
            }
        });

        buttonMindfulness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayMeditationRecommendation("Mindfulness Meditation");
            }
        });

        buttonVisualization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayMeditationRecommendation("Visualization Meditation");
            }
        });

        // Find the "Random Exercise" button by its ID
        Button buttonRandomExercise = findViewById(R.id.buttonRandomExercise);

        // Set a click listener for the "Random Exercise" button
        buttonRandomExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get a recommended meditation exercise
                String recommendedExercise = recommendMeditationExercise();

                // Display the recommended exercise
                displayMeditationRecommendation(recommendedExercise);
            }
        });
    }
}