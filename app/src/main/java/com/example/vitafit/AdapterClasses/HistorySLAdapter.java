package com.example.vitafit.AdapterClasses;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vitafit.Entities.HistoryBPModel;
import com.example.vitafit.Entities.HistorySLModel;
import com.example.vitafit.R;

import java.util.ArrayList;

public class HistorySLAdapter extends RecyclerView.Adapter<HistorySLAdapter.ViewHolder> {

    Context context;
    ArrayList<HistorySLModel> arrSL;

    public HistorySLAdapter(Context context, ArrayList<HistorySLModel> arrSL) {
        this.context = context;
        this.arrSL = arrSL;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_sl_history_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistorySLModel model = arrSL.get(position);
        holder.glyIndex.setText(arrSL.get(position).getGly_index());
        holder.status.setText(arrSL.get(position).getStatus());
        holder.date.setText(arrSL.get(position).getDate());
        holder.time.setText(arrSL.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        {return arrSL.size();}
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView glyIndex, status, date , time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            glyIndex = itemView.findViewById(R.id.history_gly_index);
            status = itemView.findViewById(R.id.history_sl_status);
            date = itemView.findViewById(R.id.history_sl_date);
            time = itemView.findViewById(R.id.history_sl_time);


        }
    }
}
