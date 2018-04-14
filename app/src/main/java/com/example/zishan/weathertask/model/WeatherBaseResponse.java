package com.example.zishan.weathertask.model;

import com.example.zishan.weathertask.network.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown=true)
public class WeatherBaseResponse extends BaseResponse{

    private ArrayList<WeatherListData> list = null;

    public ArrayList<WeatherListData> getList() {
        return list;
    }

    public void setList(ArrayList<WeatherListData> list) {
        this.list = list;
    }
}
