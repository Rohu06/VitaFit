package com.example.vitafit.AdapterClasses;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vitafit.R;
import com.example.vitafit.User.Exercise;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private final List<Exercise> exerciseList;
    private final OnExerciseClickListener onExerciseClickListener;

    public ExerciseAdapter(List<Exercise> exerciseList, OnExerciseClickListener onExerciseClickListener) {
        this.exerciseList = exerciseList;
        this.onExerciseClickListener = onExerciseClickListener;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        holder.imageViewExerciseIcon.setImageResource(exercise.getIconResId());
        holder.textViewExerciseTitle.setText(exercise.getTitle());
        holder.textViewExerciseDescription.setText(exercise.getDescription());
        holder.textViewExerciseCategory.setText(exercise.getCategory());

        // Set click listener
        holder.itemView.setOnClickListener(v -> onExerciseClickListener.onExerciseClick(exercise));
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewExerciseIcon;
        TextView textViewExerciseTitle;
        TextView textViewExerciseDescription;
        TextView textViewExerciseCategory;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewExerciseIcon = itemView.findViewById(R.id.imageViewExerciseIcon);
            textViewExerciseTitle = itemView.findViewById(R.id.textViewExerciseTitle);
            textViewExerciseDescription = itemView.findViewById(R.id.textViewExerciseDescription);
            textViewExerciseCategory = itemView.findViewById(R.id.textViewExerciseCategory);
        }
    }

    public interface OnExerciseClickListener {
        void onExerciseClick(Exercise exercise);
    }
}