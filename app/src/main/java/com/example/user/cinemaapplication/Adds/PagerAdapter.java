package com.example.user.cinemaapplication.Adds;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class PagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> screens = new ArrayList<Fragment>();

    private Context context;
    private List<AtomicBoolean> flags = new ArrayList<AtomicBoolean>();

    public PagerAdapter(FragmentManager fm, Context context, List<Fragment> screens) {
        super(fm);
        this.context = context;

        for(Fragment screen : screens)
            addScreen(screen, null);

        notifyDataSetChanged();
    }

    public PagerAdapter(FragmentManager fm, Context context, Map<Fragment, Bundle> screens) {
        super(fm);
        this.context = context;

        for(Fragment screen : screens.keySet())
            addScreen(screen, screens.get(screen));

        notifyDataSetChanged();
    }


    private void addScreen(Fragment clazz, Bundle args) {
        screens.add(clazz);
//        screens.add(Fragment.instantiate(context, clazz.getName(), args));
        flags.add(new AtomicBoolean(true));
    }

    public boolean isEnabled(int position) {
        return flags.get(position).get();
    }

    public void setEnabled(int position, boolean enabled) {
        AtomicBoolean flag = flags.get(position);
        if(flag.get() != enabled) {
            flag.set(enabled);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        int n = 0;
        for(AtomicBoolean flag : flags) {
            if(flag.get())
                n++;
        }
        return n;
    }

    @Override
    public Fragment getItem(int position) {
        return screens.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE; // To make notifyDataSetChanged() do something
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    private List<Fragment> getEnabledScreens() {
        List<Fragment> res = new ArrayList<Fragment>();
        for(int n = 0; n < screens.size(); n++) {
            if(flags.get(n).get())
                res.add(screens.get(n));
        }
        return res;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // We map the requested position to the position as per our screens array list
        Fragment fragment = getEnabledScreens().get(position);
        int internalPosition = screens.indexOf(fragment);
        return super.instantiateItem(container, internalPosition);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Fragment fragment = getEnabledScreens().get(position);
        if(fragment instanceof TitledFragment)
            return ((TitledFragment)fragment).getTitle(context);
        return super.getPageTitle(position);
    }

    public static interface TitledFragment {
        public String getTitle(Context context);
    }

}


//    private final List<Fragment> mFragmentList = new ArrayList<>();
//    private final List<String> mFragmentTitleList = new ArrayList<>();
//
//    /*maybe re-do completely*/
//    /*was told that title-list can be not processed*/
//
//    public PagerAdapter(FragmentManager manager) {
//        super(manager);
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        return mFragmentList.get(position);
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
//        return null;
//    }
//
//    public void removeFragment(Fragment fragment,String title){
//        mFragmentList.remove(fragment);
//        mFragmentTitleList.remove(title);
//        notifyDataSetChanged();
//    }
//
//    public void addFragToPos(Fragment fragment, String title, int count){
//        mFragmentList.add(count,fragment);
//        mFragmentTitleList.add(count,title);
//        notifyDataSetChanged();
//    }
//
//    public void removeFragAtPos(Fragment fragment, String title, int count){
//
//        mFragmentList.remove(fragment);
//        if(mFragmentList.contains(fragment)){
//            mFragmentList.remove(count);
//        }
//
//        mFragmentTitleList.remove(title);
//        if(mFragmentTitleList.contains(title)){
//            mFragmentTitleList.remove(count);
//        }
//
//        notifyDataSetChanged();
//
//    }