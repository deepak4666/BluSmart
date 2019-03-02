package com.example.blusmart.data.remote;

import com.example.blusmart.data.model.Duty;
import com.example.blusmart.utils.Commons;

import java.util.List;


import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    String DUTY_LIST = "/api/v1/app/dutyid/list";

    String DUTY_DETAIL = "/api/v1/app/duty/{duty_id}";
    String DUTY_UPDATE = "/api/v1/app/update/duty/{duty_id}";


    @GET(DUTY_LIST)
    Observable<List<Integer>> getDutyList();

    @GET(DUTY_DETAIL)
    Single<Duty> getDuty(@Path("duty_id") int dutyId);

    @PUT(DUTY_UPDATE)
    Single<Duty> updateDuty(@Path("duty_id") int dutyId, @Body RequestBody data);


}
