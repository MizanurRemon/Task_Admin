package com.example.themelooks_admin.Model.Products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Products_variant_response {
    @SerializedName("productsID")
    @Expose
    public Integer productsID;
    @SerializedName("color")
    @Expose
    public String color;
    @SerializedName("size")
    @Expose
    public String size;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("price_range")
    @Expose
    public String priceRange;

    public Integer getProductsID() {
        return productsID;
    }

    public void setProductsID(Integer productsID) {
        this.productsID = productsID;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }
}
