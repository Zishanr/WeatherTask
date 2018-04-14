package com.example.zishan.weathertask.ui;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.zishan.weathertask.R;
import com.example.zishan.weathertask.adapters.CityAdapter;
import com.example.zishan.weathertask.adapters.CityWeatherAdapter;
import com.example.zishan.weathertask.databinding.ActivityMainBinding;
import com.example.zishan.weathertask.model.CitisList;
import com.example.zishan.weathertask.model.WeatherBaseResponse;
import com.example.zishan.weathertask.network.BaseCallback;
import com.example.zishan.weathertask.network.RequestController;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;

    private ProgressDialog mProgressDialog;
    private ArrayList<CitisList> citisLists = null;
    private CityWeatherAdapter cityWeatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initUI();
    }

    // Setting UI
    private void initUI() {
        activityMainBinding.rvCityWeather.setLayoutManager(new LinearLayoutManager(MainActivity.this, OrientationHelper.VERTICAL, false));
        ItemDecorator itemDecoration = new ItemDecorator(MainActivity.this, R.dimen.fourteen_dp);
        activityMainBinding.rvCityWeather.addItemDecoration(itemDecoration);

        loadCityList();
    }

    // Fetching City list from Constant file and setting Spinner adapter
    private void loadCityList() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            citisLists = mapper.readValue(Constants.CITIES_LIST, new TypeReference<ArrayList<CitisList>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (citisLists != null) {
            CityAdapter cityAdapter = new CityAdapter(this, R.layout.city_item, citisLists);
            activityMainBinding.spinnerCityList.setAdapter(cityAdapter);
        }

        activityMainBinding.spinnerCityList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fetchData(citisLists.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // Fetching data and populating adapter with data
    private void fetchData(int cityId) {
        showProgressBar();
        Call<WeatherBaseResponse> weatherResponseCall = RequestController.getInstance().createService().getCityWeatherList(cityId, Constants.HOUR_TYPE, Constants.APP_ID);
        weatherResponseCall.enqueue(new BaseCallback<WeatherBaseResponse>() {
            @Override
            public void onSuccess(WeatherBaseResponse response) {
                hideProgressBar();
                if (response.getList() != null && response.getList().size() > 0) {
                    activityMainBinding.tvNoRecordFound.setVisibility(View.GONE);

                    if (cityWeatherAdapter == null) {
                        cityWeatherAdapter = new CityWeatherAdapter(response.getList());
                        activityMainBinding.rvCityWeather.setAdapter(cityWeatherAdapter);
                    } else {
                        cityWeatherAdapter.notifyDataSetChanged();
                    }

                } else {
                    activityMainBinding.tvNoRecordFound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFail(Call<WeatherBaseResponse> call) {
                hideProgressBar();
                Toast.makeText(MainActivity.this, R.string.Error, Toast.LENGTH_SHORT).show();
            }
        });


    }

    // Show Loading Progress bar
    public void showProgressBar() {
        mProgressDialog = ProgressDialog.show(new ContextThemeWrapper(MainActivity.this,
                android.R.style.Theme_Holo_Light), "", getString(R.string.loading), true, false);
    }

    // Hide Loading Progress bar
    public void hideProgressBar() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing())
                mProgressDialog.dismiss();
            mProgressDialog = null;
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
