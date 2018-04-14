package com.example.zishan.weathertask.network;

import com.example.zishan.weathertask.model.WeatherBaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("city")
    Call<WeatherBaseResponse> getCityWeatherList(@Query("id") int id,
                                                   @Query("type") String type,
                                                 @Query("appid") String appid);
}
