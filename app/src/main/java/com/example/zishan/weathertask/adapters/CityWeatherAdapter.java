package com.example.zishan.weathertask.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zishan.weathertask.R;
import com.example.zishan.weathertask.databinding.CityWeatherItemBinding;
import com.example.zishan.weathertask.model.WeatherListData;

import java.util.ArrayList;

public class CityWeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<WeatherListData> weatherListData;

    public CityWeatherAdapter(ArrayList<WeatherListData> weatherListData) {
        this.weatherListData = weatherListData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_weather_item, parent, false);
        viewHolder = new CityWeatherViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((CityWeatherViewHolder) holder).cityWeatherItemBinding.setMainData(weatherListData.get(position).getMain());
        ((CityWeatherViewHolder) holder).cityWeatherItemBinding.setWindData(weatherListData.get(position).getWind());

        if(weatherListData.get(position).getWeather().size() == 1) {
            int weatherDescriptionPosition = 0;
            ((CityWeatherViewHolder) holder).cityWeatherItemBinding.setWeatherData(weatherListData.get(position).getWeather().get(weatherDescriptionPosition));
        }
    }

    @Override
    public int getItemCount() {
        return (weatherListData != null) ? weatherListData.size() : 0;
    }

    private class CityWeatherViewHolder extends RecyclerView.ViewHolder {

        CityWeatherItemBinding cityWeatherItemBinding;

        CityWeatherViewHolder(View itemView) {
            super(itemView);
            cityWeatherItemBinding = DataBindingUtil.bind(itemView);
        }
    }
}
