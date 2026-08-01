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


public class FragmentReportYearly extends Fragment {
    RadioButton pressureYRadioBtn, sugarYRadioBtn, caloriesYRadioBtn;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_report_yearly, container, false);

        // Hooks
        pressureYRadioBtn = view.findViewById(R.id.yearly_pressure_rBtn);
        caloriesYRadioBtn = view.findViewById(R.id.yearly_calories_rBtn);
        sugarYRadioBtn = view.findViewById(R.id.yearly_sugar_rBtn);

        //by default loaded fragment
        loadYearlyFragment(new FragmentReportYearlyPressure(), true);

        // replacing fragment by clicking on radio buttons
        pressureYRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadYearlyFragment(new FragmentReportYearlyPressure(), false);
            }
        });
        sugarYRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadYearlyFragment(new FragmentReportYearlySugar(),false);
            }
        });
        caloriesYRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadYearlyFragment(new FragmentReportYearlyCalories(),false);
            }
        });

        return view;
    }

    private void loadYearlyFragment(Fragment fragment, boolean flag){
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (flag) {
            fragmentTransaction.add(R.id.yearly_container, fragment);
        }else{
            fragmentTransaction.replace(R.id.yearly_container, fragment);
        }

        fragmentTransaction.commit();

    }
}