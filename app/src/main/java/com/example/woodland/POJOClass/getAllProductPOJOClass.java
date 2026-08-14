package com.example.woodland.POJOClass;

public class getAllProductPOJOClass
{
    String id,product_name,product_image,product_category,product_price,product_rating;


    public getAllProductPOJOClass(String id, String product_name, String product_image, String product_category, String product_price, String product_rating) {
        this.id = id;
        this.product_name = product_name;
        this.product_image = product_image;
        this.product_category = product_category;
        this.product_price = product_price;
        this.product_rating = product_rating;
    }

    public String getId() {
        return id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public String getProduct_category() {
        return product_category;
    }

    public String getProduct_price() {
        return product_price;
    }

    public String getProduct_rating() {
        return product_rating;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public void setProduct_rating(String product_rating) {
        this.product_rating = product_rating;
    }
}
