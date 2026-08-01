package com.example.vitafit.AdapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vitafit.Entities.HistoryBPModel;

import com.example.vitafit.R;

import java.util.ArrayList;

public class HistoryBPAdapter extends RecyclerView.Adapter<HistoryBPAdapter.ViewHolder> {
    Context context;
    ArrayList<HistoryBPModel> arrBP;

    public HistoryBPAdapter(Context context, ArrayList<HistoryBPModel> arrBP) {
        this.context = context;
        this.arrBP = arrBP;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_bp_history_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryBPModel model = arrBP.get(position);
        holder.sys.setText(arrBP.get(position).getSystolic());
        holder.dia.setText(arrBP.get(position).getDiastolic());
        holder.pulse.setText(arrBP.get(position).getPulse());
        holder.date.setText(arrBP.get(position).getDate());
        holder.time.setText(arrBP.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        {return arrBP.size();}
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView sys, dia, pulse, date, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sys = itemView.findViewById(R.id.history_systolic);
            dia = itemView.findViewById(R.id.history_diastolic);
            pulse = itemView.findViewById(R.id.history_pulse);
            date = itemView.findViewById(R.id.history_bp_date);
            time = itemView.findViewById(R.id.history_bp_time);
        }
    }
}
