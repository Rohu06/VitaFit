package com.example.vitafit.User;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.vitafit.R;


public class FragmentReportMonthly extends Fragment {
    RadioButton pressureMRadioBtn, sugarMRadioBtn, caloriesMRadioBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_monthly, container, false);

        // Hooks
        pressureMRadioBtn = view.findViewById(R.id.monthly_pressure_rBtn);
        caloriesMRadioBtn = view.findViewById(R.id.monthly_calories_rBtn);
        sugarMRadioBtn = view.findViewById(R.id.monthly_sugar_rBtn);

        // default fragment load
        loadMonthlyFragment(new FragmentReportMonthlyPressure(),true);

        // replacing fragment by clicking on radio buttons
        pressureMRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMonthlyFragment(new FragmentReportMonthlyPressure(), false);
            }
        });
        caloriesMRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMonthlyFragment(new FragmentReportMonthlyCalories(),false);
            }
        });
        sugarMRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMonthlyFragment(new FragmentReportMonthlySugar(),false);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void loadMonthlyFragment(Fragment fragment, boolean flag){
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (flag) {
            fragmentTransaction.add(R.id.monthly_container, fragment);
        }else{
            fragmentTransaction.replace(R.id.monthly_container, fragment);
        }

        fragmentTransaction.commit();

    }
}