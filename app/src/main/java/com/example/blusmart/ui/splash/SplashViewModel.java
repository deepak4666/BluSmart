package com.example.blusmart.ui.splash;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SplashViewModel extends ViewModel {

    private CompositeDisposable disposable;
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @Inject
    public SplashViewModel() {
        disposable = new CompositeDisposable();
        splashWait();
    }



    public LiveData<Boolean> getLoading() {
        return loading;
    }

    private void splashWait() {
        loading.setValue(true);
        disposable.add(Observable.interval(7000, TimeUnit.MILLISECONDS)
                .single(0L)
                .map(new Function<Long, Boolean>() {
                    @Override
                    public Boolean apply(Long aLong) throws Exception {
                        return aLong==7000L;

                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Boolean>(){
                    @Override
                    public void onSuccess(Boolean value) {
                        loading.setValue(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loading.setValue(false);
                    }
                }));

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }

}
