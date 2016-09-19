package com.kondee.thenewlegend.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.common.collect.Lists;
import com.kondee.thenewlegend.R;
import com.kondee.thenewlegend.adapter.MainFragmentPagerAdapter;
import com.kondee.thenewlegend.databinding.ActivityMainBinding;
import com.kondee.thenewlegend.fragment.MainFragment;
import com.kondee.thenewlegend.fragment.SecondFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Kondee";
    ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initInstance();
    }

    private void initInstance() {

        setSupportActionBar(binding.toolbar);

        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(),this);
        adapter.addFragment(MainFragment.newInstance(),R.drawable.home);
        adapter.addFragment(SecondFragment.newInstance(),R.drawable.star);

        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        for(int i = 0;i<adapter.icons.size();i++){
            binding.tabLayout.getTabAt(i).setIcon(adapter.icons.get(i));
        }

    }
}
