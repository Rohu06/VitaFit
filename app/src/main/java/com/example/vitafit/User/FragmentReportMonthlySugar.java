package com.example.vitafit.User;

import android.annotation.SuppressLint;
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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;


public class FragmentReportMonthlySugar extends Fragment {
    LineChart lineChartSLMonthly;

    public FragmentReportMonthlySugar() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_monthly_sugar, container, false);
        lineChartSLMonthly = view.findViewById(R.id.lc_sl_monthly_report);

        LineDataSet lineDataSet = new LineDataSet(monthlySugarLevel(), "Sugar Level");
        lineDataSet.setColor(getContext().getColor(R.color.blue_light));
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleColor(getContext().getColor(R.color.blue_dark));

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        LineData data = new LineData(dataSets);
        lineChartSLMonthly.setData(data);


        XAxis xAxis = lineChartSLMonthly.getXAxis();

        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        lineChartSLMonthly.setDragEnabled(true);
        lineChartSLMonthly.setVisibleXRangeMaximum(30);
        lineChartSLMonthly.getXAxis().setAxisMaximum(30);
//        lineChartSLWeekly.getAxisLeft().setDrawGridLines(false);
//        lineChartSLWeekly.getAxisRight().setDrawGridLines(false);
//        lineChartSLWeekly.getAxisRight().setDrawLabels(false);
//        lineChartSLWeekly.getAxisRight().setDrawAxisLine(false);
//        lineChartSLWeekly.getXAxis().setDrawGridLines(false);

        xAxis.setTextColor(getContext().getColor(R.color.color_BW));
        lineChartSLMonthly.getAxisRight().setTextColor(getContext().getColor(R.color.color_BW));
        lineChartSLMonthly.getAxisLeft().setTextColor(getContext().getColor(R.color.color_BW));
        lineChartSLMonthly.getDescription().setTextColor(getContext().getColor(R.color.color_BW));
        data.setValueTextColor(getContext().getColor(R.color.color_BW));
        lineChartSLMonthly.invalidate();


        return view;
    }

    // for adding data into arraylist for Diastolic
    private ArrayList<Entry> monthlySugarLevel() {
        // session calling for getting user Email
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("email_pref", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("userEmail", "").toString();
        DbHelper helper = new DbHelper(getContext());
        int id = 0;
        Cursor cursor = helper.getUserData(email);
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }


        ArrayList<Entry> slValues = new ArrayList<>();
        // fetching BP table last entry
        Cursor cursorSL = helper.getWeeklySL(id);


        for (int i = 0; i < cursorSL.getCount(); i++) {
            cursorSL.moveToNext();
            slValues.add(new BarEntry( i, Float.parseFloat(cursorSL.getString(1))));

        }

        return slValues;
    }
}