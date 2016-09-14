package com.kondee.thenewlegend.activity;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.kondee.thenewlegend.R;
import com.kondee.thenewlegend.databinding.ActivityMainBinding;
import com.kondee.thenewlegend.fragment.MainFragment;

import junit.framework.Assert;

import static com.kondee.thenewlegend.R.id.contentContainer;
import static com.kondee.thenewlegend.R.id.reloadBtn;
import static com.kondee.thenewlegend.R.id.useLogo;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Kondee";
    ActivityMainBinding binding;
    private GestureDetectorCompat gestureDetectorCompat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initInstance();
    }

    private void initInstance() {

        setSupportActionBar(binding.toolbar);

        getSupportFragmentManager().beginTransaction()
                .replace(contentContainer, MainFragment.newInstance(), "MainFragment")
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.reloadBtn) {

            MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag("MainFragment");
            if (mainFragment != null) {
                mainFragment.loadPeopleData();
                mainFragment.showAlertDialog();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
