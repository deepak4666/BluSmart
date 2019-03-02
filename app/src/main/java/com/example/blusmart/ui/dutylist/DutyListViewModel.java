package com.example.blusmart.ui.dutylist;

import android.util.Log;

import com.example.blusmart.data.model.Duty;
import com.example.blusmart.data.remote.DutyApi;

import java.util.List;


import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DutyListViewModel extends ViewModel {

    private DutyApi dutyApi;
    private CompositeDisposable disposable;

    private final MutableLiveData<List<Duty>> dutyList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @Inject
    DutyListViewModel(DutyApi dutyApi) {
        this.dutyApi = dutyApi;
        disposable = new CompositeDisposable();
        fetchDuties();
    }

    LiveData<List<Duty>> getDutyList() {
        return dutyList;
    }

    LiveData<Boolean> getError() {
        return repoLoadError;
    }

    LiveData<Boolean> getLoading() {
        return loading;
    }

    private void fetchDuties() {
        loading.setValue(true);
        disposable.add(dutyApi.getDutyList()
                .flatMapIterable(ids -> {
                    Log.e("ids", ids.toString());
                    return ids;
                })
                .flatMap(id -> dutyApi.getDuty(id)
                        .map(duty -> {
                            duty.setId(id);
                            return duty;
                        }).toObservable()
                )
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Duty>>() {
                    @Override
                    public void onSuccess(List<Duty> value) {
                        repoLoadError.setValue(false);
                        dutyList.setValue(value);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        repoLoadError.setValue(true);
                        loading.setValue(false);
                    }
                }));
    }

    public void updateDuty(Duty duty){
        loading.setValue(true);
        dutyApi.updateDuty(duty)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new SingleObserver<Duty>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Duty data) {
                        repoLoadError.setValue(false);
                        loading.setValue(false);
                        fetchDuties();
                    }

                    @Override
                    public void onError(Throwable e) {
                        repoLoadError.setValue(true);
                        loading.setValue(false);
                    }
                });
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
