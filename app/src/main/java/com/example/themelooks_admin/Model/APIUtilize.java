package com.example.themelooks_admin.Model;


import com.example.themelooks_admin.Model.AddProducts.Add_products_API;
import com.example.themelooks_admin.Model.Login.Login_API;
import com.example.themelooks_admin.Model.Products.Products_API;

public class APIUtilize {
    public APIUtilize() {
    }

    public static final String BASE_URL = "http://hoh.fsbbazar.com/";


    public static Login_API loginApi() {
        return Retrofit_client.getClient(BASE_URL).create(Login_API.class);
    }

    public static Add_products_API addProductsApi(){
        return Retrofit_client.getClient(BASE_URL).create(Add_products_API.class);
    }

    public  static Products_API productsApi(){
        return Retrofit_client.getClient(BASE_URL).create(Products_API.class);
    }

}
