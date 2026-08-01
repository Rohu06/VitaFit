package com.example.vitafit.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vitafit.R;

public class steps_counting extends AppCompatActivity implements SensorEventListener {

    private TextView stepsTextView, caloriesTextView, distanceTextView;
    private SensorManager sensorManager;
    private Sensor stepSensor;
    private boolean isSensorAvailable;
    private int stepsCount = 0;
    private int previousSteps = 0;

    private static final float STEP_LENGTH = 0.75f; // Average step length in meters
    private static final float CALORIES_PER_STEP = 0.04f; // Approximate calories burned per step

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_counting);

        // Initialize UI elements
        stepsTextView = findViewById(R.id.tv_steps);
        caloriesTextView = findViewById(R.id.tv_calories);
        distanceTextView = findViewById(R.id.tv_distance);

        // Get stored step count
        loadPreviousSteps();

        // Initialize sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isSensorAvailable = stepSensor != null;
        }

        if (!isSensorAvailable) {
            stepsTextView.setText("Step Sensor Not Available");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isSensorAvailable) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isSensorAvailable) {
            sensorManager.unregisterListener(this);
        }
        saveSteps(); // Save steps when app is paused
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            if (previousSteps == 0) {
                previousSteps = (int) event.values[0]; // Set initial value
            }
            stepsCount = (int) event.values[0] - previousSteps;
            updateUI();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No action needed
    }

    private void updateUI() {
        stepsTextView.setText(String.valueOf(stepsCount));

        // Calculate distance covered (meters)
        float distance = stepsCount * STEP_LENGTH;
        distanceTextView.setText(String.format("%.2f meters", distance));

        // Calculate calories burned
        float calories = stepsCount * CALORIES_PER_STEP;
        caloriesTextView.setText(String.format("%.2f kcal", calories));
    }

    private void saveSteps() {
        SharedPreferences sharedPreferences = getSharedPreferences("StepCounterPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("previousSteps", previousSteps);
        editor.apply();
    }

    private void loadPreviousSteps() {
        SharedPreferences sharedPreferences = getSharedPreferences("StepCounterPrefs", MODE_PRIVATE);
        previousSteps = sharedPreferences.getInt("previousSteps", 0);
    }
}
