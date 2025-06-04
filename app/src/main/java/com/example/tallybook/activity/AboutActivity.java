package com.example.tallybook.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.tallybook.R;

public class AboutActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
    public void back(View view){
        finish();
    }
}
