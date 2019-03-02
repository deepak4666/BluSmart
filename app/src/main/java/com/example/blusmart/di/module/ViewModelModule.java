package com.example.blusmart.di.module;

import com.example.blusmart.di.util.ViewModelKey;
import com.example.blusmart.ui.dutylist.DutyListViewModel;
import com.example.blusmart.ui.splash.SplashViewModel;
import com.example.blusmart.utils.ViewModelFactory;

import javax.inject.Singleton;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
public abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    abstract ViewModel bindSplashViewModel(SplashViewModel splashViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(DutyListViewModel.class)
    abstract ViewModel bindDutyListViewModel(DutyListViewModel dutyListViewModel);

    /*@Binds
    @IntoMap
    @ViewModelKey(DutyDetailViewModel.class)
    abstract ViewModel bindDutyDetailViewModel(DutyDetailViewModel detailsViewModel);
*/
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}