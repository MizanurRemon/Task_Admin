package com.example.themelooks_admin.Model.Login;

import com.google.gson.annotations.SerializedName;

public class Login_response {
    @SerializedName("adminID")
    private String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
