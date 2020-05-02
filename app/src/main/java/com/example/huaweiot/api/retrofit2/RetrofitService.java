package com.example.huaweiot.api.retrofit2;

import com.example.huaweiot.api.Beans.CommandResponse;
import com.example.huaweiot.api.Beans.HomeData;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("/home_data")
    Observable<HomeData> getHomeData(@Query("room") String room);

    @POST("/commands")
    @Headers("Content-Type:application/json")
    Observable<CommandResponse> postCommands(@Body RequestBody body);

    @GET("/subscription")
    Observable<Object> subscribeService();

}
