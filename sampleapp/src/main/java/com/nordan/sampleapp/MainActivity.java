package com.nordan.sampleapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViewById(R.id.btn_about_us).setOnClickListener(click -> showAboutUs());
        findViewById(R.id.btn_settings).setOnClickListener(click -> showSettings());
    }

    private void showSettings() {
        startActivity(new Intent(this, SettingsSample.class));
    }

    private void showAboutUs() {
        startActivity(new Intent(this, AboutUsSample.class));
    }
}
