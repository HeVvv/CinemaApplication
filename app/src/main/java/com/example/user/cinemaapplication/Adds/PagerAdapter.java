package com.example.user.cinemaapplication.Adds;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.user.cinemaapplication.Activites.AuditChoosingActivity;
import com.example.user.cinemaapplication.Activites.HistoryListActivity;
import com.example.user.cinemaapplication.Activites.QRScanActivity;

public class PagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;


    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AuditChoosingActivity tab1 = new AuditChoosingActivity();
                return tab1;

            case 1:
                QRScanActivity tab2 = new QRScanActivity();
                return tab2;
            case 2:
                HistoryListActivity tab3 = new HistoryListActivity();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}