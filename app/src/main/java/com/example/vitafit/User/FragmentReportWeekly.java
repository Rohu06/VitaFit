package com.example.vitafit.User;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;


import com.example.vitafit.R;


public class FragmentReportWeekly extends Fragment {

    RadioButton pressureWRadioBtn, sugarWRadioBtn, caloriesWRadioBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report_weekly, container, false);

        // Hooks
        pressureWRadioBtn = view.findViewById(R.id.weekly_pressure_rBtn);
        caloriesWRadioBtn = view.findViewById(R.id.weekly_calories_rBtn);
        sugarWRadioBtn = view.findViewById(R.id.weekly_sugar_rBtn);

        // default fragment load
        loadWeeklyFragment(new FragmentReportWeeklyPressure(),true);

        // replacing fragment by clicking on radio buttons
        pressureWRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWeeklyFragment(new FragmentReportWeeklyPressure(), false);
            }
        });
        caloriesWRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWeeklyFragment(new FragmentReportWeeklyCalories(),false);
            }
        });
        sugarWRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWeeklyFragment(new FragmentReportWeeklySugar(),false);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void loadWeeklyFragment(Fragment fragment, boolean flag){
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (flag) {
            fragmentTransaction.add(R.id.weekly_container, fragment);
        }else{
            fragmentTransaction.replace(R.id.weekly_container, fragment);
        }

        fragmentTransaction.commit();

    }
}