package com.example.themelooks_admin.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.themelooks_admin.Model.Login.Login_repositories;
import com.example.themelooks_admin.Model.Login.Login_response;


public class LoginViewModel extends AndroidViewModel {
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Login_response> getMessage(String phone, String password) {

        return Login_repositories.getInstance().getMessage(phone, password);

    }
}
