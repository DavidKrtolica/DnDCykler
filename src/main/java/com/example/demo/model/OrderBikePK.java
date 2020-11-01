package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class OrderBikePK implements Serializable {
    private int orderId;
    private int bikeId;

    @Column(name = "order_id", nullable = false)
    @Id
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Column(name = "bike_id", nullable = false)
    @Id
    public int getBikeId() {
        return bikeId;
    }

    public void setBikeId(int bikeId) {
        this.bikeId = bikeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderBikePK that = (OrderBikePK) o;

        if (orderId != that.orderId) return false;
        if (bikeId != that.bikeId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + bikeId;
        return result;
    }
}
