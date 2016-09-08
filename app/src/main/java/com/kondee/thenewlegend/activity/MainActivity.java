package com.kondee.thenewlegend.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kondee.thenewlegend.R;
import com.kondee.thenewlegend.databinding.ActivityMainBinding;
import com.kondee.thenewlegend.fragment.MainFragment;

import static com.kondee.thenewlegend.R.id.contentContainer;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initInstance();
    }

    private void initInstance() {

        setSupportActionBar(binding.toolbar);

        getSupportFragmentManager().beginTransaction()
                .replace(contentContainer, MainFragment.newInstance(),"MainFragment")
                .commit();
    }
}
