package com.example.zishan.weathertask.network;

import android.support.annotation.NonNull;

import com.example.zishan.weathertask.ui.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseCallback<T extends BaseResponse> implements Callback<T> {

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if (response.body() != null && response.isSuccessful()) {
            if (response.body().getCod().equals(Constants.SUCCESS_RESPONSE_CODE))
                onSuccess(response.body());
            else {
                onFail(call);
            }
        } else {
            onFail(call);
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        onFail(call);

    }

    public abstract void onSuccess(T response);

    public abstract void onFail(Call<T> call);
}
