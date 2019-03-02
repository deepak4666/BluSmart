package com.example.blusmart.data.remote;

import com.example.blusmart.data.model.Duty;
import com.google.gson.Gson;

import java.util.List;


import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DutyApi {

    private final ApiService apiService;

    @Inject
    Gson gson;

    @Inject
    public DutyApi(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<List<Integer>> getDutyList() {
        return apiService.getDutyList();
    }

    public Single<Duty> getDuty(int id) {
        return apiService.getDuty(id);
    }

    public Single<Duty> updateDuty(Duty duty) {
        return apiService.updateDuty(duty.getId(),getRequestBody(duty));
    }

    private RequestBody getRequestBody(Duty duty) {
        return RequestBody.create(MediaType.parse("application/json"), gson.toJson(duty));
    }

}
