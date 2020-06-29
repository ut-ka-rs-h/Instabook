package com.practice.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class SocialMediaActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabAdapter tabAdapter;
    private Toolbar toolbar;
    private TabLayout tabLayout;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        setTitle(R.string.app_name);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewPager);
        tabAdapter = new TabAdapter(getSupportFragmentManager(), 0);
        viewPager.setAdapter(tabAdapter);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager, true);

    }
}