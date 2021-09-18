package com.example.themelooks_admin.Model.Login;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Login_API {
    @GET("/login")
    Call<Login_response> getResponse(@Query("phone") String phone,
                                          @Query("password") String password);
}
