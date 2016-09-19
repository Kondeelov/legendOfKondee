package com.kondee.thenewlegend.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private final Context context;
    private List<Fragment> fragments = new ArrayList<>();
    public List<Integer> icons = new ArrayList<>();

    public MainFragmentPagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context =context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(Fragment fragment, int icon){
        fragments.add(fragment);
        icons.add(icon);
    }
}
