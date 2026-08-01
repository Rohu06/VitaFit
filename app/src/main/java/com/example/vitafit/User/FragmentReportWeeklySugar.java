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
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;


public class FragmentReportWeeklySugar extends Fragment {

    LineChart lineChartSLWeekly;

    public FragmentReportWeeklySugar() {
        // Required empty public constructor
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_weekly_sugar, container, false);

        lineChartSLWeekly = view.findViewById(R.id.lc_sl_weekly_report);

        LineDataSet lineDataSet = new LineDataSet(weeklySugarLevel(), "Sugar Level");
        lineDataSet.setColor(getContext().getColor(R.color.blue_light));
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleColor(getContext().getColor(R.color.blue_dark));

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        LineData data = new LineData(dataSets);
        lineChartSLWeekly.setData(data);

        String[] days = new String[]{"Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat"};
        XAxis xAxis = lineChartSLWeekly.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        lineChartSLWeekly.setDragEnabled(true);
        lineChartSLWeekly.setVisibleXRangeMaximum(8);

//        lineChartSLWeekly.getAxisLeft().setDrawGridLines(false);
//        lineChartSLWeekly.getAxisRight().setDrawGridLines(false);
//        lineChartSLWeekly.getAxisRight().setDrawLabels(false);
//        lineChartSLWeekly.getAxisRight().setDrawAxisLine(false);
//        lineChartSLWeekly.getXAxis().setDrawGridLines(false);


        xAxis.setTextColor(getContext().getColor(R.color.color_BW));
        lineChartSLWeekly.getAxisRight().setTextColor(getContext().getColor(R.color.color_BW));
        lineChartSLWeekly.getAxisLeft().setTextColor(getContext().getColor(R.color.color_BW));
        lineChartSLWeekly.getDescription().setTextColor(getContext().getColor(R.color.color_BW));
        data.setValueTextColor(getContext().getColor(R.color.color_BW));

        lineChartSLWeekly.invalidate();
        return view;
    }

    // for adding data into arraylist for Diastolic
    private ArrayList<Entry> weeklySugarLevel() {
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