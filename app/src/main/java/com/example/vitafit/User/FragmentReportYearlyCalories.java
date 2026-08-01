package com.example.vitafit.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vitafit.Database.DbHelper;
import com.example.vitafit.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;


public class FragmentReportYearlyCalories extends Fragment {

    BarChart barChartCIYearly;
    public FragmentReportYearlyCalories() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_yearly_calories, container, false);

        // Hook
        barChartCIYearly = view.findViewById(R.id.bc_ci_yearly_report);

        // for sugar Level graph
        BarDataSet dataSetCI = new BarDataSet(caloriesIntake(), "Calories");
        dataSetCI.setColor(getContext().getColor(R.color.Yellowish));
        BarData barData = new BarData();
        barData.addDataSet(dataSetCI);
        barChartCIYearly.setData(barData);
        barChartCIYearly.setVisibleXRangeMaximum(565);
        barChartCIYearly.invalidate();
        barChartCIYearly.getXAxis().setAxisMaximum(365);
        XAxis xAxis = barChartCIYearly.getXAxis();
        xAxis.setTextColor(getContext().getColor(R.color.color_BW));
        barChartCIYearly.getAxisRight().setTextColor(getContext().getColor(R.color.color_BW));
        barChartCIYearly.getAxisLeft().setTextColor(getContext().getColor(R.color.color_BW));
        barChartCIYearly.getDescription().setTextColor(getContext().getColor(R.color.color_BW));
        dataSetCI.setValueTextColor(getContext().getColor(R.color.color_BW));

        return  view;
    }


    // for adding data into arraylist for sugar level graph
    private ArrayList<BarEntry> caloriesIntake() {
        // session calling for getting user Email
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("email_pref", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("userEmail", "").toString();
        DbHelper helper = new DbHelper(getContext());
        int id = 0;
        Cursor cursor = helper.getUserData(email);
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }



        ArrayList<BarEntry> ciValues = new ArrayList<>();

        Cursor cursorCI = helper.getWeeklyCI(id);
        for (int i = 0; i < cursorCI.getCount(); i++) {
            cursorCI.moveToNext();
            ciValues.add(new BarEntry( i, Float.parseFloat(cursorCI.getString(1))));
        }
        return ciValues;
    }
}