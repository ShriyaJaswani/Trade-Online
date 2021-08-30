package com.project.caaryajava;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Model implements Serializable{
//    var productName : String? = null,
//    var marketPrice : String? = null,
//    var storePrice : String? = null,
//    var category : String? = null,
//    var description : String? = null
    String productName;
    int marketPrice;
    int storePrice;
    String category;
    String description;
    String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(int marketPrice) {
        this.marketPrice = marketPrice;
    }

    public int getStorePrice() {
        return storePrice;
    }

    public void setStorePrice(int storePrice) {
        this.storePrice = storePrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Model(String productName, int marketPrice, int storePrice, String category, String description, String image) {
        this.productName = productName;
        this.marketPrice = marketPrice;
        this.storePrice = storePrice;
        this.category = category;
        this.description = description;
        this.image = image;
    }

}
