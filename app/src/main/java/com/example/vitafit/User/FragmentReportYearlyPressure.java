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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;


public class FragmentReportYearlyPressure extends Fragment {

    BarChart barChartBPYearly;
    public FragmentReportYearlyPressure() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_yearly_pressure, container, false);

        // Hooks
        barChartBPYearly = view.findViewById(R.id.bc_bp_yearly_report);


        // session calling for getting user Email
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("email_pref", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("userEmail", "").toString();

        int id = 0;

        // db helper class object
        DbHelper helper = new DbHelper(getContext());

        // fetching user name
        Cursor cursor = helper.getUserData(email);
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }
        int finalId = id;



//        String[] date = new String[cursorTotalBP.getCount()];
//
//        for (int i = 0; i < date.length; i++) {
//            cursorTotalBP.moveToNext();
//            date[i] = cursorTotalBP.getString(4);
//        }

        // for sugar Level graph
        BarDataSet diastolicBDS = new BarDataSet(diastolic(), "Diastolic");
        diastolicBDS.setColor(getContext().getColor(R.color.Yellowish));
        BarDataSet systolicBDS = new BarDataSet(systolic(), "Systolic");
        diastolicBDS.setColor(getContext().getColor(R.color.greenish));
        BarData BPBarData = new BarData(diastolicBDS,systolicBDS);
        barChartBPYearly.setData(BPBarData);


        XAxis xAxis = barChartBPYearly.getXAxis();

        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        barChartBPYearly.setDragEnabled(true);
        barChartBPYearly.setVisibleXRangeMaximum(365);

        barChartBPYearly.getAxisLeft().setDrawGridLines(false);
        barChartBPYearly.getXAxis().setDrawGridLines(false);
        barChartBPYearly.getAxisRight().setDrawAxisLine(false);

        barChartBPYearly.getAxisRight().setDrawGridLines(false);


        float barSpace = 0.05f;
        float groupSpace = 0.20f;

        BPBarData.setBarWidth(0.35f);

        barChartBPYearly.getXAxis().setAxisMaximum(0);
        barChartBPYearly.getXAxis().setAxisMaximum(0+barChartBPYearly.getBarData().getGroupWidth(groupSpace,barSpace)*365);
        barChartBPYearly.getAxisLeft().setAxisMinimum(0);

        barChartBPYearly.groupBars(0, groupSpace,barSpace);

        xAxis.setTextColor(getContext().getColor(R.color.color_BW));
        barChartBPYearly.getAxisRight().setTextColor(getContext().getColor(R.color.color_BW));
        barChartBPYearly.getAxisLeft().setTextColor(getContext().getColor(R.color.color_BW));
        barChartBPYearly.getDescription().setTextColor(getContext().getColor(R.color.color_BW));
        systolicBDS.setValueTextColor(getContext().getColor(R.color.color_BW));
        diastolicBDS.setValueTextColor(getContext().getColor(R.color.color_BW));


        barChartBPYearly.invalidate();




        return  view;
    }
    // for adding data into arraylist for Diastolic
    private ArrayList<BarEntry> systolic() {
        // session calling for getting user Email
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("email_pref", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("userEmail", "").toString();
        DbHelper helper = new DbHelper(getContext());
        int id = 0;
        Cursor cursor = helper.getUserData(email);
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }


        ArrayList<BarEntry> diastolicValues = new ArrayList<>();

        // fetching BP table last entry
        Cursor cursorSIA = helper.getWeeklyBP(id);
        for (int i = 0; i < cursorSIA.getCount(); i++) {
            cursorSIA.moveToNext();
            diastolicValues.add(new BarEntry( i, Float.parseFloat(cursorSIA.getString(1))));

        }

        return diastolicValues;
    }
    // for adding data into arraylist for Diastolic
    private ArrayList<BarEntry> diastolic() {
        ArrayList<BarEntry> systolicValues = new ArrayList<>();
        // session calling for getting user Email
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("email_pref", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("userEmail", "").toString();
        DbHelper helper = new DbHelper(getContext());
        int id = 0;
        Cursor cursor = helper.getUserData(email);
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }

        // fetching BP table last entry
        Cursor cursorDIA = helper.getWeeklyBP(id);


        for (int i = 0; i < cursorDIA.getCount(); i++) {
            cursorDIA.moveToNext();
            systolicValues.add(new BarEntry( i, Float.parseFloat(cursorDIA.getString(2))));

        }
        return systolicValues;
    }
}