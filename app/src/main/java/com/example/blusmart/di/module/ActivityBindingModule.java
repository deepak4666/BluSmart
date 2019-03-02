package com.example.blusmart.di.module;

import com.example.blusmart.ui.dutylist.DutyListScreen;
import com.example.blusmart.ui.splash.SplashScreen;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector()
    abstract SplashScreen bindSplashScreen();

    @ContributesAndroidInjector()
    abstract DutyListScreen bindDutyListScreen();


}