package com.example.themelooks_admin.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.themelooks_admin.Model.AddProducts.Add_product_response;
import com.example.themelooks_admin.Model.AddProducts.Add_products_repositories;
import com.example.themelooks_admin.Model.AddProducts.Price_response;
import com.example.themelooks_admin.Model.Login.Login_repositories;
import com.example.themelooks_admin.Model.Login.Login_response;

public class AddProductsViewModel extends AndroidViewModel {
    public AddProductsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Price_response> getPrice(String color, String size) {

        return Add_products_repositories.getInstance().getPrice(color, size);

    }

    public LiveData<Add_product_response> getProduct(String name,  String description, String image) {

        return Add_products_repositories.getInstance().getProduct(name, description, image);

    }
}
