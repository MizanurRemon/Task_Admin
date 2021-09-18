package com.example.themelooks_admin.Model.Products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Products_response {
    @SerializedName("productsID")
    @Expose
    public Integer productsID;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("color")
    @Expose
    public String color;
    @SerializedName("size")
    @Expose
    public String size;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("image")
    @Expose
    public String image;

    public Integer getProductsID() {
        return productsID;
    }

    public void setProductsID(Integer productsID) {
        this.productsID = productsID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
