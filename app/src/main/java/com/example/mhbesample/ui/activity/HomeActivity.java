package com.example.mhbesample.ui.activity;

import android.os.Bundle;

import com.example.mhbesample.R;

import retrofit2.http.HEAD;

public class HomeActivity extends BaseActivity {
    private final String HEADER = "None";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public String getHeaderTitle() {
        return HEADER;
    }

    @Override
    public int getLayoutById() {
        return R.layout.activity_home;
    }

    @Override
    public void getViewById() {

    }

    @Override
    public void manageToolBar() {
    }

}
