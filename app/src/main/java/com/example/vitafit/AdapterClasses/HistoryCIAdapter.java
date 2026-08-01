package com.example.vitafit.AdapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vitafit.Entities.HistoryCIModel;
import com.example.vitafit.Entities.HistorySLModel;
import com.example.vitafit.R;

import java.util.ArrayList;

public class HistoryCIAdapter extends RecyclerView.Adapter<HistoryCIAdapter.ViewHolder> {


    Context context;
    ArrayList<HistoryCIModel> arrCI;

    public HistoryCIAdapter(Context context, ArrayList<HistoryCIModel> arrCI) {
        this.context = context;
        this.arrCI = arrCI;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_ci_history_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryCIModel model = arrCI.get(position);
        holder.calories.setText(arrCI.get(position).getCalories());
        holder.date.setText(arrCI.get(position).getDate());
        holder.time.setText(arrCI.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return arrCI.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView calories, date , time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            calories = itemView.findViewById(R.id.history_calories);
            date = itemView.findViewById(R.id.history_ci_date);
            time = itemView.findViewById(R.id.history_ci_time);

        }
    }
}
