package com.example.user.cinemaapplication.Adds;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.user.cinemaapplication.Activites.AuditChoosingActivity;
import com.example.user.cinemaapplication.Activites.HistoryListActivity;
import com.example.user.cinemaapplication.Activites.QRScanActivity;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class PagerAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        public void removeFragment(Fragment fragment,String title){
            mFragmentList.remove(fragment);
            mFragmentTitleList.remove(title);
        }
}


//    private List<Fragment> mFragmentList = new ArrayList<>();
//    private List<String> mFragmentTitleList = new ArrayList<>();
//
//    public PagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
//        super(fm);
//        this.mFragmentList = fragmentList;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//
//        switch (position) {
//            case 0:
//                HistoryListActivity tab1 = new HistoryListActivity();
//                return tab1;
//            case 1:
//                AuditChoosingActivity tab2 = new AuditChoosingActivity();
//                return tab2;
//            case 2:
//                QRScanActivity tab3 = new QRScanActivity();
//                return tab3;
//            default:
//                return null;
//        }
//    }
//
//    @Override
//    public int getCount() {
//        return mFragmentList.size();
//    }
//
//    public void addFragment(Fragment fragment, String title) {
//        mFragmentList.add(fragment);
//        mFragmentTitleList.add(title);
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mFragmentTitleList.get(position);
//    }
//
//    public void removeFragment(Fragment fragment,int position) {
//        mFragmentTitleList.remove(position);
//        mFragmentList.remove(fragment);
//        notifyDataSetChanged();
//    }