package com.example.petpal;

public class GigModel {
    String title;
    String price;
    int image;

    public GigModel(String title, String price, int image) {
        this.title = title;
        this.price = price;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }
}
