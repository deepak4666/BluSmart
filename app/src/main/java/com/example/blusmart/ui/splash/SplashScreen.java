package com.example.blusmart.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import com.example.blusmart.R;
import com.example.blusmart.base.BaseActivity;
import com.example.blusmart.ui.dutylist.DutyListScreen;
import com.example.blusmart.utils.ViewModelFactory;
import com.google.gson.Gson;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class SplashScreen extends BaseActivity {

    @Inject
    ViewModelFactory viewModelFactory;
    private SplashViewModel viewModel;

    @Override
    protected int layoutRes() {
        return R.layout.splash_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel.class);
        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {

                if (isLoading) {
                    openDutyListScreen();
                }
            }
        });
    }

    private void openDutyListScreen() {
        Intent intent = new Intent(this, DutyListScreen.class);
        startActivity(intent);
        finish();
    }

}
