package com.example.themelooks_admin.Model.AddProducts;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Add_products_API {

    @FormUrlEncoded
    @POST("/price")
    Call<Price_response> priceResponse(@Field("color") String color,
                                       @Field("size") String size);

    @FormUrlEncoded
    @POST("/add_products")
    Call<Add_product_response> addResponse(@Field("name") String name,
                                           @Field("color") String color,
                                           @Field("size") String size,
                                           @Field("price") String price,
                                           @Field("description") String description,
                                           @Field("image") String imageData);
}
