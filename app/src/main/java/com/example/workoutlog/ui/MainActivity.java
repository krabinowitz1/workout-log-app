package com.example.workoutlog.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.workoutlog.R;
import com.example.workoutlog.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.viewpager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), binding.viewpager.getContext()));
        binding.tabLayout.setupWithViewPager(binding.viewpager, true);
    }
}