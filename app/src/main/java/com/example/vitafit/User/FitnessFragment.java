package com.example.vitafit.User;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vitafit.AdapterClasses.ExerciseAdapter;
import com.example.vitafit.R;
import com.example.vitafit.User.Exercise;

import java.util.ArrayList;
import java.util.List;

public class FitnessFragment extends Fragment {

    private RecyclerView recyclerView;
    private ExerciseAdapter exerciseAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fitness, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewExercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create and set adapter
        exerciseAdapter = new ExerciseAdapter(getExerciseList(), exercise -> {
            // Handle exercise item click
            openExerciseDetails(exercise);
        });
        recyclerView.setAdapter(exerciseAdapter);

        return view;
    }

    private List<Exercise> getExerciseList() {
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("Box Breathing", "A simple breathing technique to reduce stress.", "Breathing", R.drawable.outline_self_improvement_24, "https://youtu.be/tEmt1Znux58?feature=shared"));
        exercises.add(new Exercise("Morning Stretches", "Stretches to start your day with energy.", "Stretching", R.drawable.ic_stretch, "https://example.com/morning-stretches-video"));
        exercises.add(new Exercise("5-Minute Mindfulness", "A quick mindfulness session to calm your mind.", "Mindfulness", R.drawable.ic_mindful, "https://example.com/mindfulness-audio"));
        exercises.add(new Exercise("10-Minute Home Workout", "A quick workout to stay fit at home.", "Quick Workouts", R.drawable.outline_fitness_center_24, "https://example.com/home-workout-video"));
        exercises.add(new Exercise("Deep Breathing", "Relax with deep, slow breaths.", "Breathing", R.drawable.lungs, "https://example.com/deep-breathing-video"));
        exercises.add(new Exercise("Neck and Shoulder Stretch", "Relieve tension in your neck and shoulders.", "Stretching", R.drawable.ic_stretch, "https://example.com/neck-shoulder-stretch-video"));
        exercises.add(new Exercise("Guided Meditation", "A 10-minute meditation to clear your mind.", "Mindfulness", R.drawable.outline_self_improvement_24, "https://example.com/guided-meditation-audio"));
        exercises.add(new Exercise("Plank Challenge", "Strengthen your core with a 5-minute plank.", "Quick Workouts", R.drawable.outline_fitness_center_24, "https://example.com/plank-challenge-video"));
        exercises.add(new Exercise("Evening Wind-Down Stretch", "Relaxing stretches before bed.", "Stretching", R.drawable.ic_stretch, "https://example.com/evening-stretch-video"));
        return exercises;
    }

    private void openExerciseDetails(Exercise exercise) {
        // Get the video URL from the exercise
        String videoUrl = exercise.getMediaUrl();

        // Create an Intent to open the URL in a browser or video player
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(videoUrl));

        // Check if there's an app to handle the intent
        if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // If no app can handle the intent, show a message to the user
            Toast.makeText(requireContext(), "No app found to handle this action.", Toast.LENGTH_SHORT).show();
        }
    }
}