package com.example.flexpictures.model;

//Model for our data
public class ItemModel {
    private int image;
    private String name, age, city;

    public ItemModel() {
    }

    public ItemModel(int image, String name, String age, String city) {
        this.image = image;
        this.name = name;
        this.age = age;
        this.city = city;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }
}
