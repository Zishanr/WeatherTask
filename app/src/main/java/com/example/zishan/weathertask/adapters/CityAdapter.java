package com.example.zishan.weathertask.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.zishan.weathertask.R;
import com.example.zishan.weathertask.model.CitisList;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends ArrayAdapter<CitisList> {


    private Context context;
    private int layoutResourceId;
    private ArrayList<CitisList> citisListsData;

    public CityAdapter(Context context, int layoutResourceId, ArrayList<CitisList> citisListsData) {
        super(context, layoutResourceId, citisListsData);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.citisListsData = citisListsData;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }


    private View createItemView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CityHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new CityHolder();
            holder.cityName = (TextView) row.findViewById(R.id.tv_city_name);

            row.setTag(holder);
        } else {
            holder = (CityHolder) row.getTag();
        }


        holder.cityName.setText(citisListsData.get(position).getName());

        return row;
    }


    static class CityHolder {
        TextView cityName;
    }
}
