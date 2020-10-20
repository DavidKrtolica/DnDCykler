package com.example.demo.model;

public class BikeOrder {
    private int orderId;
    private int bikeId;
    private int orderPrice;

    // Constructor 1
    public BikeOrder() {}

    // Constructor 2

    public BikeOrder(int orderId, int bikeId, int orderPrice) {
        this.orderId = orderId;
        this.bikeId = bikeId;
        this.orderPrice = orderPrice;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBikeId() {
        return bikeId;
    }

    public void setBikeId(int bikeId) {
        this.bikeId = bikeId;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }
}
