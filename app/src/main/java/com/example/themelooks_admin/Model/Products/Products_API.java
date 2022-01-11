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

    @FormUrlEncoded
    @POST("/products_variant_details")
    Call<List<Products_variant_response>> getProductVariant(@Field("productsID") String productsID);

    @FormUrlEncoded
    @POST("/products_details")
    Call<Products_response> getProductDetails(@Field("productsID") String productsID);
}
