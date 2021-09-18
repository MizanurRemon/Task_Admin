package com.example.themelooks_admin.Model.AddProducts;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.themelooks_admin.Model.APIUtilize;
import com.example.themelooks_admin.Model.Login.Login_API;
import com.example.themelooks_admin.Model.Login.Login_repositories;
import com.example.themelooks_admin.Model.Login.Login_response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_products_repositories {

    private static Add_products_repositories add_products_repositories;
    Add_products_API addProductsApi;
    MutableLiveData<Price_response> message;
    MutableLiveData<Add_product_response> message2;

    private Add_products_repositories() {
        addProductsApi = APIUtilize.addProductsApi();
    }

    public synchronized static Add_products_repositories getInstance() {
        if (add_products_repositories == null) {
            return new Add_products_repositories();
        }
        return add_products_repositories;
    }

    public @NonNull
    MutableLiveData<Price_response> getPrice( @NonNull String color, @NonNull String size) {

        if (message == null) {
            message = new MutableLiveData<>();
        }

        Call<Price_response> call = addProductsApi.priceResponse(color, size);

        call.enqueue(new Callback<Price_response>() {
            @Override
            public void onResponse(Call<Price_response> call, Response<Price_response> response) {

                if (response.isSuccessful()) {
                    Price_response priceResponse = response.body();
                    message.postValue(priceResponse);
                }
            }

            @Override
            public void onFailure(Call<Price_response> call, Throwable t) {
                Price_response response = new Price_response();
                message.postValue(response);
                Log.d("errorxx", t.getMessage());
            }
        });
        return message;
    }

    public @NonNull
    MutableLiveData<Add_product_response> getProduct( @NonNull String name,@NonNull String color, @NonNull String size,@NonNull String price,@NonNull String description, @NonNull String imageData) {

        if (message2 == null) {
            message2 = new MutableLiveData<>();
        }

        Call<Add_product_response> call = addProductsApi.addResponse(name, color, size, price, description, imageData);

        call.enqueue(new Callback<Add_product_response>() {
            @Override
            public void onResponse(Call<Add_product_response> call, Response<Add_product_response> response) {

                if (response.isSuccessful()) {
                    Add_product_response add_product_response = response.body();
                    message2.postValue(add_product_response);
                }
            }

            @Override
            public void onFailure(Call<Add_product_response> call, Throwable t) {
                Add_product_response response = new Add_product_response();
                response.setMessage("invalid");
                message2.postValue(response);
            }
        });
        return message2;
    }
}
