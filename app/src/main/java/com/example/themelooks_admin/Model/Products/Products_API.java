package com.example.themelooks_admin.Model.Products;

import com.example.themelooks_admin.Model.AddProducts.Price_response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Products_API {

    @GET("/products")
    Call<List<Products_response>> getProducts();
}
