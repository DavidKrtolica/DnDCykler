package com.example.demo.model;

public class Bike {
    private int bikeId;
    private String type;
    private String state;
    private String brand;
    private String frameSize;
    private int price;

    // Constructor 1
    public Bike() {}

    // Constructor 2
    public Bike(int bikeId, String type, String state, String brand, String frameSize, int price) {
        this.bikeId = bikeId;
        this.type = type;
        this.state = state;
        this.brand = brand;
        this.frameSize = frameSize;
        this.price = price;
    }

    // Getters and Setters
    public int getBikeId() {
        return bikeId;
    }

    public void setBikeId(int bikeId) {
        this.bikeId = bikeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFrameSize() {
        return frameSize;
    }

    public void setFrameSize(String frameSize) {
        this.frameSize = frameSize;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
