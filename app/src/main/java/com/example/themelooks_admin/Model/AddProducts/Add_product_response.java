package com.example.themelooks_admin.Model.AddProducts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Add_product_response {

    @SerializedName("message")
    @Expose
    public String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
