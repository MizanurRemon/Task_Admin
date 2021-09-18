package com.example.themelooks_admin.Model.Login;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.themelooks_admin.Model.APIUtilize;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_repositories {

    private static Login_repositories login_repositories;
    Login_API loginApi;
    MutableLiveData<Login_response> message;

    private Login_repositories() {
        loginApi = APIUtilize.loginApi();
    }

    public synchronized static Login_repositories getInstance() {
        if (login_repositories == null) {
            return new Login_repositories();
        }
        return login_repositories;
    }

    public @NonNull
    MutableLiveData<Login_response> getMessage( @NonNull String phone, @NonNull String password) {

        if (message == null) {
            message = new MutableLiveData<>();
        }

        Call<Login_response> call = loginApi.getResponse(phone, password);

        call.enqueue(new Callback<Login_response>() {
            @Override
            public void onResponse(Call<Login_response> call, Response<Login_response> response) {

                if (response.isSuccessful()) {
                    Login_response login_response = response.body();
                    message.postValue(login_response);
                }
            }

            @Override
            public void onFailure(Call<Login_response> call, Throwable t) {
                Login_response response = new Login_response();
                response.setUserID("-1");
                message.postValue(response);
            }
        });
        return message;
    }
}
