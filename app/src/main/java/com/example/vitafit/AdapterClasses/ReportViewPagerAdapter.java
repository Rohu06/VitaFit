package com.example.vitafit.AdapterClasses;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.vitafit.User.FragmentReportMonthly;
import com.example.vitafit.User.FragmentReportWeekly;
import com.example.vitafit.User.FragmentReportYearly;

public class ReportViewPagerAdapter extends FragmentPagerAdapter {


    public ReportViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        if (position==0) {
            return  new FragmentReportWeekly();
        } else if (position == 1) {
            return new FragmentReportMonthly();
        } else {
            return new FragmentReportYearly();
        }

    }


    @Override
    public int getCount() {
        return 3;// no. of tabs
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0) {
            return  "Weekly";
        } else if (position == 1) {
            return "Monthly";
        } else {
            return "Yearly";
        }
    }
}
