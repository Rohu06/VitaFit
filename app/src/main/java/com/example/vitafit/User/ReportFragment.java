package com.example.vitafit.User;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vitafit.AdapterClasses.ReportViewPagerAdapter;
import com.example.vitafit.R;
import com.google.android.material.tabs.TabLayout;


public class ReportFragment extends Fragment {
    TabLayout reportTab;
    ViewPager reportViewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report, container, false);
        //Hooks
        reportTab = view.findViewById(R.id.tab);
        reportViewPager = view.findViewById(R.id.view_pager);


        ReportViewPagerAdapter adapter = new ReportViewPagerAdapter(getChildFragmentManager());
        reportViewPager.setAdapter(adapter);
        reportTab.setupWithViewPager(reportViewPager);


        return view;
    }
}